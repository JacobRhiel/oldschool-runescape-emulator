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


    }

}