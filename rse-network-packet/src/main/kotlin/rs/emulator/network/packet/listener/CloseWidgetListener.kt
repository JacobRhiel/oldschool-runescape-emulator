package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.actor.player.widgets.WidgetViewport
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.CloseWidgetMessage


/**
 *
 * @author javatar
 */

class CloseWidgetListener : GamePacketListener<CloseWidgetMessage> {
    override fun handle(channel: Channel, player: Player, message: CloseWidgetMessage) {
        player.widgetViewport.close(WidgetViewport.OverlayFrame.VIEW_PORT)
    }
}