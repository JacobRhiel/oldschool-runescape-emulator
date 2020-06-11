package rs.emulator.application

import com.google.common.util.concurrent.ServiceManager
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.koin.dsl.module
import rs.emulator.application.hooks.shutdown.ShutdownHook
import rs.emulator.engine.service.CyclicEngineService
import rs.emulator.engine.service.schedule.CyclicDelaySchedule
import java.time.Duration

/**
 *
 * @author Chk
 */
object RuneScapeEmulator : KoinComponent
{

    private val engine by inject<CyclicEngineService>()

    private val shutdownHook by inject<ShutdownHook>()

    private val serviceManager = ServiceManager(
        listOf(
            engine
        )
    )

    private fun init()
    {

        Runtime.getRuntime().addShutdownHook(shutdownHook)

        serviceManager.startAsync()

        serviceManager.awaitHealthy(Duration.ofMinutes(1))

    }

    @JvmStatic
    fun main(args: Array<String>)
    {

        val mod = module {

            single { CyclicEngineService() }

            single { CyclicDelaySchedule() }

            single { ShutdownHook() }

        }

        startKoin {

            modules(mod)

            init()

        }

    }

}