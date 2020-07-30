package rs.emulator.entity.action.condition

import com.google.common.base.MoreObjects
import java.util.concurrent.atomic.AtomicInteger

/**
 * A [SuspendableCondition] that waits for the given amount of cycles before
 * permitting the coroutine to continue its logic.
 *
 * @param cycles
 * The amount of game cycles that must pass before the coroutine can continue.
 *
 * @author Tom <rspsmods@gmail.com>
 */
class WaitCondition(cycles: Int) : SuspendableCondition()
{

    private val cyclesLeft = AtomicInteger(cycles)

    override fun resume(): Boolean = cyclesLeft.decrementAndGet() <= 0

    override fun toString(): String = MoreObjects.toStringHelper(this).add("cycles", cyclesLeft).toString()

}