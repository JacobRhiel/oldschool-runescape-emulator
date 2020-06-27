package rs.emulator.plugins.extensions

import org.pf4j.ExtensionPoint
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.widgets.IWidget

/**
 *
 * @author javatar
 */

interface WidgetInteractionExtensionPoint : ExtensionPoint {

    fun onClick(player : IPlayer, widget : IWidget, child : Int)

}