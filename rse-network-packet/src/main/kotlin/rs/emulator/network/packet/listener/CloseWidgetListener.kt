package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.CloseWidgetMessage
import rs.emulator.widgets.WidgetViewport

/**
 *
 * @author javatar
 */

class CloseWidgetListener : GamePacketListener<CloseWidgetMessage> {
    override fun handle(channel: Channel, player: Player, message: CloseWidgetMessage) {
        player.widgetViewport.closeAll(WidgetViewport.Frames.VIEW_PORT)
    }
}