package rs.emulator.world

import rs.emulator.entity.`object`.IObject
import rs.emulator.entity.actor.npc.INpc
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.temp.ISpell

object GameWorld {

    lateinit var players: List<IPlayer>
    lateinit var npcs: List<INpc>

    //TODO - make an entire system for spells, waiting on regions for objects
    lateinit var objects: List<IObject>
    lateinit var spells: List<ISpell>

}