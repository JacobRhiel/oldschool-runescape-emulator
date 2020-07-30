package rs.emulator.entity.action

import rs.emulator.entity.action.condition.WaitCondition
import rs.emulator.entity.action.policy.ActionCancellationPolicy
import rs.emulator.entity.action.priority.ActionPriority
import rs.emulator.entity.action.state.ActionSuspensionState
import rs.emulator.entity.action.step.SuspendableStep
import rs.emulator.utilities.logger.error
import kotlin.coroutines.*

/**
 *
 * @author Chk
 *
 * Credits[Tom <rspsmods@gmail.com>] - [nextStep], [SuspendableStep]
 *
 */
open class QueuedAction(val priority: ActionPriority = ActionPriority.NORMAL,
                        val cancellationPolicy: ActionCancellationPolicy = ActionCancellationPolicy.APPEND_TO_EXISTING
) : Continuation<Unit>
{

    var started: Boolean = false

    lateinit var coroutine: Continuation<Unit>

    override val context: CoroutineContext = EmptyCoroutineContext

    val state: ActionSuspensionState
        get() = if(started && nextStep != null)
                ActionSuspensionState.SUSPENDED
            else if(!started)
                ActionSuspensionState.PREPARED
            else if(started && nextStep == null)
                ActionSuspensionState.STARTED
            else
                ActionSuspensionState.FINISHED

    /**
     * The next [SuspendableStep], if any, that must be handled once a [SuspendableCondition]
     * returns [SuspendableCondition.resume] as true.
     */
    private var nextStep: SuspendableStep? = null

    open val trailingAction: QueuedAction? = null

    override fun resumeWith(result: Result<Unit>)
    {
        nextStep = null
        result.exceptionOrNull()?.let { e -> error("Error with plugin!", e) }
    }

    fun start()
    {

        println("attempting to start? wtf")

        check(!started) { "Action[$this] has already been started." }

        started = !started

        println("action started? : $started")

        coroutine.resume(Unit)

    }

    open fun perform()
    {

        val next = nextStep ?: return

        if(next.condition.resume())
        {

            next.continuation.resume(Unit)

        }

    }

    open fun terminate()
    {



    }

    /**
     * Wait for the specified amount of game cycles [cycles] before
     * continuing the logic associated with this task.
     */
    suspend fun wait(cycles: Int): Unit = suspendCoroutine {
        check(cycles > 0) { "Wait cycles must be greater than 0." }
        nextStep = SuspendableStep(WaitCondition(cycles), it)
    }

    override fun equals(other: Any?): Boolean {
        val o = other as? QueuedAction ?: return false
        return super.equals(o) && o.coroutine == coroutine
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + coroutine.hashCode()
        return result
    }

}