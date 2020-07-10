package rs.emulator.plugins.extensions

import org.pf4j.ExtensionPoint
import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface ItemActionExtensionPoint : ExtensionPoint {

    fun IPlayer.interact(itemId : Int, option : Int, interfaceId : Int, componentId : Int)

}