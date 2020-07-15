package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface CommandFactory : ExtensionPoint {

    fun execute(player: IPlayer, command: String)
    fun hasRights(player: IPlayer): Boolean

}