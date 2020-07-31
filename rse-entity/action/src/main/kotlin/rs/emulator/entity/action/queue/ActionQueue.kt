package rs.emulator.entity.action.queue

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import rs.emulator.entity.action.QueuedAction
import rs.emulator.entity.action.policy.ActionCancellationPolicy.CANCEL_EXISTING
import rs.emulator.entity.action.priority.ActionPriority
import rs.emulator.entity.action.state.ActionSuspensionState
import rs.emulator.utilities.koin.get
import java.util.*
import kotlin.coroutines.createCoroutine

/**
 *
 * @author Chk
 */
class ActionQueue
{

    private val dispatcher: ExecutorCoroutineDispatcher = get()

    private val queue = LinkedList<QueuedAction>()

    fun submit(priority: ActionPriority = ActionPriority.NORMAL, block: suspend QueuedAction.(CoroutineScope) -> Unit) = submit(QueuedAction(priority), block)

    fun submit(action: QueuedAction, block: suspend QueuedAction.(CoroutineScope) -> Unit)
    {

        val suspendable = suspend { block(action, CoroutineScope(dispatcher)) }

        suspendable.createCoroutine(completion = action).apply { action.coroutine = this }

        cancelPending(action)

    }

    fun cycle()
    {

        while(true)
        {

            val action = queue.peekFirst() ?: return

            println("action: " + action.state)

            if (action.state == ActionSuspensionState.PREPARED)
                if (!action.started)
                    action.start()

            action.perform()

            if (action.state == ActionSuspensionState.FINISHED)
            {
                queue.remove(action)
                continue
            }

            break
        }

    }

    fun cancelPending(action: QueuedAction)
    {

        when(action.cancellationPolicy)
        {

            CANCEL_EXISTING ->
            {

                queue.forEach { it.terminate() }

                queue.clear()

                queue.offerFirst(action)

            }

            else ->
            {

                queue.offerLast(action)

            }

        }

    }

}