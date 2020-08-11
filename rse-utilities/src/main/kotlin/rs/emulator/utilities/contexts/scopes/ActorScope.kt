package rs.emulator.utilities.contexts.scopes

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

/**
 *
 * @author javatar
 */

data class ActorScope(override val coroutineContext: CoroutineContext = newSingleThreadContext("actorContext")) : CoroutineScope