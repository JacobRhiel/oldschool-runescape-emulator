package rs.emulator.plugins.extensions.factories.widgets.actions

import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface CloseWidgetAction {

    fun handleCloseWidgetRequest(
        player: IPlayer
    )

}