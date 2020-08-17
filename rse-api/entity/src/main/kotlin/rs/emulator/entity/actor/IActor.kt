package rs.emulator.entity.actor

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import rs.emulator.entity.IEntity
import rs.emulator.entity.actor.affects.AffectHandler
import rs.emulator.entity.actor.attributes.ActorAttributes
import rs.emulator.entity.skills.SkillManager
import rs.emulator.regions.zones.RegionZone

@ExperimentalCoroutinesApi
interface IActor : IEntity
{

    val actorAttributes: ActorAttributes
    val skillManager: SkillManager
    val currentZone: MutableStateFlow<RegionZone>
    val affectHandler: AffectHandler<*>

    fun resetMovement()

}