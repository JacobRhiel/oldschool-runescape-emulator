package rs.emulator.service.login.worker

import com.google.common.util.concurrent.AbstractScheduledService
import org.koin.core.KoinComponent
import org.koin.core.inject
import rs.emulator.service.login.LoginResult
import rs.emulator.service.login.network.message.LoginRequestMessage
import rs.emulator.service.login.network.message.LoginResponseMessage
import java.util.concurrent.TimeoutException

/**
 *
 * @author Chk
 */
class LoginWorkerService : KoinComponent, AbstractScheduledService()
{

    @ExperimentalStdlibApi
    private val queue = ArrayDeque<LoginWorker>()

    override fun scheduler() = inject<LoginWorkerSchedule>().value

    @OptIn(ExperimentalStdlibApi::class)
    fun submit(request: LoginRequestMessage): LoginWorker
    {

        val worker = LoginWorker(request)

        submit(worker)

        return worker

    }

    @ExperimentalStdlibApi
    private fun submit(worker: LoginWorker) = queue.addLast(worker)

    @ExperimentalStdlibApi
    override fun runOneIteration()
    {

        scheduler().lastCycle = System.currentTimeMillis()

        val iterator = queue.subList(0, if(queue.size > 40) 40 else queue.size).listIterator()

        while(iterator.hasNext())
        {

            val worker = iterator.next()

            val remainingMilliseconds = scheduler().remaining

            try
            {

                val start = System.currentTimeMillis()

                //todo: retries?
                val loginResult = LoginResult(worker.execute().first)

                worker.request.channel.write(LoginResponseMessage(worker.request.isaac, loginResult))

                queue.remove(worker)

                val end = System.currentTimeMillis()

            }
            catch (e: TimeoutException)
            {

                println("error")

            }

        }

    }

}