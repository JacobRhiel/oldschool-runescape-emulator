package rs.emulator.entity.actor

import rs.emulator.entity.IEntity
import rs.emulator.entity.actor.attributes.ActorAttributes
import rs.emulator.entity.skills.SkillManager
import rs.emulator.skills.SkillAttributes

interface IActor : IEntity
{

    val actorAttributes: ActorAttributes
    val skillManager: SkillManager

}