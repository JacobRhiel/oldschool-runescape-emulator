package rs.emulator.entity.actor.effects

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.entity.actor.IActor

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
interface IAffect<C : IActor, S : IActor> {

    val target: C
    val source: S

    fun applyAffect() : Boolean

}