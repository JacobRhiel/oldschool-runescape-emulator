package rs.emulator.world.repository

import rs.emulator.entity.actor.npc.Npc
import rs.emulator.entity.player.Player
import rs.emulator.world.GameWorld

/**
 *
 * @author Chk
 */
object WorldRepository {

    val players = ArrayList<Player>(2048)//RandomizedIndexMap(2048)

    val npcs = ArrayList<Npc>(2048)

    val nextPlayerIndex: Int
        get() = players.size + 1

    init {

        GameWorld.players = players

        GameWorld.npcs = npcs

    }

}