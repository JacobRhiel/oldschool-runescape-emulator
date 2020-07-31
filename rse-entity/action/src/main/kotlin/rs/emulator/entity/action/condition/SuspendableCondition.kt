package rs.emulator.entity.action.condition

/**
 * A condition that must be met for a suspended coroutine to continue.
 *
 * @author Tom <rspsmods@gmail.com>
 */
abstract class SuspendableCondition
{

    /**
     * Whether or not the coroutine can continue its logic.
     */
    abstract fun resume(): Boolean

}