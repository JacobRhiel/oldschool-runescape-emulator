package rs.emulator.network.packet.listener

import rs.emulator.entity.actor.player.messages.IWidgetMessages
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ReportAbuseMessage

/**
 *
 * @author javatar
 */

class ReportAbuseListener : GamePacketListener<ReportAbuseMessage> {
    override fun handle(
        player: Player,
        message: ReportAbuseMessage
    ) {

        player.messagesFromType<IWidgetMessages>()
            .sendChatMessage("${message.name} - ${message.abuseType} - ${message.isStaff}")

    }
}