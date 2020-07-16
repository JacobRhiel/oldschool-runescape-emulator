package rs.emulator.world.repository

import rs.emulator.entity.player.Player
import rs.emulator.world.GameWorld

/**
 *
 * @author Chk
 */
object WorldRepository {

    val players = ArrayList<Player>(2048)//RandomizedIndexMap(2048)

    init {

        GameWorld.players = players

    }

}