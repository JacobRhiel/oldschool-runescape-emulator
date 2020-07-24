package rs.emulator.entity.actor.npc

import rs.dusk.engine.path.Finder
import rs.emulator.entity.actor.Actor
import rs.emulator.entity.attributes.Attributes
import rs.emulator.region.WorldCoordinate
import rs.emulator.skills.SkillAttributes

class Npc(index: Int, override val id: Int) : Actor(index), INpc
{

    constructor(index: Int, id: Int, coordinate: WorldCoordinate) : this(index, id)
    {
        this.coordinate.set(coordinate)
    }

    //todo: size override by definition

    override val searchPattern: Finder = pathFinder.aa

    override val skillAttributes: SkillAttributes = SkillAttributes()

    override val attributes: Attributes = Attributes()

}