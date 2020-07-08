package rs.emulator.plugins.extensions

import org.pf4j.ExtensionPoint
import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface CommandExtensionPoint : ExtensionPoint {

    fun execute(player : IPlayer, command : String)
    fun hasRights(player : IPlayer) : Boolean

}