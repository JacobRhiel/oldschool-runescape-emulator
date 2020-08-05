package rs.emulator.entity.actor

import rs.emulator.entity.IEntity
import rs.emulator.entity.actor.attributes.ActorAttributes
import rs.emulator.skills.SkillAttributes

interface IActor : IEntity
{

    val skillAttributes: SkillAttributes
    val actorAttributes: ActorAttributes

}