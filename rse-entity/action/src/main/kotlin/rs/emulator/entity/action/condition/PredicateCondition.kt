package rs.emulator.entity.action.condition

/**
 * A [SuspendableCondition] that waits for [predicate] to return true before
 * permitting the coroutine to continue its logic.
 *
 * @author Tom <rspsmods@gmail.com>
 */
class PredicateCondition(private val predicate: () -> Boolean) : SuspendableCondition() {

    override fun resume(): Boolean = predicate.invoke()

}