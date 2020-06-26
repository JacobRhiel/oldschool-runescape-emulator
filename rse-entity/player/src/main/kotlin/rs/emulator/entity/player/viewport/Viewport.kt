package rs.emulator.entity.player.viewport

import rs.emulator.entity.player.Player

/**
 *
 * @author Chk
 */
class Viewport(val myPlayer: Player)
{

    val localPlayers = mutableMapOf<Int, Player>()

    val localPlayerCount = localPlayers.size

    val globalPlayers = mutableMapOf<Int, Player>()

    val globalPlayerCount = globalPlayers.size

}