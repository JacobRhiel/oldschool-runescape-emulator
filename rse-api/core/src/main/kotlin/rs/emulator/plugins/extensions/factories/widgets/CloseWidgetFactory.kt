package rs.emulator.plugins.extensions.factories.widgets

import org.pf4j.ExtensionPoint
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.plugins.extensions.factories.widgets.actions.CloseWidgetAction

/**
 *
 * @author javatar
 */

interface CloseWidgetFactory : ExtensionPoint {

    fun registerClosingWidgetAction(
        player: IPlayer
    ) : CloseWidgetAction

}