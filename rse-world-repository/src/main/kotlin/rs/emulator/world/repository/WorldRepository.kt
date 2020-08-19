package rs.emulator.world.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.entity.actor.npc.Npc
import rs.emulator.entity.player.Player
import rs.emulator.world.GameWorld

/**
 *
 * @author Chk
 */
@ExperimentalCoroutinesApi
object WorldRepository {

    val players = ArrayList<Player>(2048)

    val npcs = ArrayList<Npc>(2048)

    val nextPlayerIndex: Int
        get() = players.size + 1

    val nextNpcIndex: Int
        get() = npcs.size

    init {

        GameWorld.players = players

        GameWorld.npcs = npcs

        npcs.add(Npc(nextNpcIndex, 1))
        npcs.add(Npc(nextNpcIndex, 3105))

    }

}