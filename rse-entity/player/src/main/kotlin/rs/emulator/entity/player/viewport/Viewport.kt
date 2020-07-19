package rs.emulator.entity.player.viewport

import rs.emulator.entity.Entity
import rs.emulator.entity.player.Player

/**
 *
 * @author Chk
 */
class Viewport(val myPlayer: Player)
{

    val localPlayers = mutableMapOf<Int, Player>()

    val localPlayerCount: Int
        get() = localPlayers.size

    val globalPlayers = mutableMapOf<Int, Player>()

    val globalPlayerCount = globalPlayers.size

    val localNpcs = mutableMapOf<Int, Entity>()

    val localNpcCount: Int
        get() = localNpcs.size

}