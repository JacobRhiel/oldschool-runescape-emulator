package rs.emulator.entity.actor.affects.impl

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.entity.actor.affects.IAffect
import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
class MessageAffect(val msg: String, override val target: IPlayer, override val source: IPlayer = target) : IAffect<IPlayer, IPlayer> {
    override fun applyAffect() : Boolean {
        target.messages().sendChatMessage(msg)
        return true
    }
}