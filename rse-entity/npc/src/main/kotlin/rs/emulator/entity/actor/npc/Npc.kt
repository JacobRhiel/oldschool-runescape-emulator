package rs.emulator.entity.actor.npc

import rs.emulator.entity.actor.Actor
import rs.emulator.skills.SkillAttributes

class Npc(override val id: Int) : Actor(), INpc {

    override val skillAttributes: SkillAttributes = SkillAttributes()

}