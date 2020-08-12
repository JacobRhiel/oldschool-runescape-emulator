package rs.emulator.network.packet.listener

import rs.emulator.entity.actor.player.widgets.WidgetViewport
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.CloseWidgetMessage


/**
 *
 * @author javatar
 */

class CloseWidgetListener : GamePacketListener<CloseWidgetMessage> {
    override fun handle(
        player: Player,
        message: CloseWidgetMessage
    ) {
        player.widgetViewport.close(WidgetViewport.OverlayFrame.VIEW_PORT)
    }
}