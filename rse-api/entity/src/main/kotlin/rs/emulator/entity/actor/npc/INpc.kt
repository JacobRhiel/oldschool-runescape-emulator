package rs.emulator.entity.actor.npc

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.entity.actor.IActor
import rs.emulator.entity.actor.affects.AffectHandler

@ExperimentalCoroutinesApi
interface INpc : IActor {

    val id: Int
    override val affectHandler: AffectHandler<INpc>

}