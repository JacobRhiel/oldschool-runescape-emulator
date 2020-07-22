package rs.emulator.entity.actor.npc

import rs.dusk.engine.path.Finder
import rs.emulator.entity.actor.Actor
import rs.emulator.skills.SkillAttributes

class Npc(override val id: Int) : Actor(), INpc
{

    //todo: size override by definition

    override val searchPattern: Finder = pathFinder.aa

    override val skillAttributes: SkillAttributes = SkillAttributes()

}