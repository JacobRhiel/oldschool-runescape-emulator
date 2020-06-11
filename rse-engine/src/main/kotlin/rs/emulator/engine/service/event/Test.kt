package rs.emulator.engine.service.event

import com.google.common.util.concurrent.ServiceManager
import rs.emulator.engine.service.CyclicEngineService
import kotlin.random.Random

/**
 *
 * @author Chk
 */
object Test
{

    @JvmStatic
    fun main(args: Array<String>)
    {

        val engine = CyclicEngineService()

        val manager = ServiceManager(listOf(engine))

        manager.startAsync()

        manager.awaitHealthy()

        engine.schedule(object : CyclicEvent()
                        {

                            override fun execute(): Boolean
                            {

                                println("executing")

                                Thread.sleep(Random.nextLong(450))

                                return true

                            }

                            override fun isComplete(): Boolean
                            {
                                return false
                            }

                        })

    }

}