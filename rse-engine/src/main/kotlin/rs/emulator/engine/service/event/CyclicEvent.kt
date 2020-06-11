package rs.emulator.engine.service.event

/**
 *
 * @author Chk
 */
abstract class CyclicEvent : Event
{

    override fun execute(): Boolean
    {
        return true
    }

}