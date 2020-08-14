package rs.emulator.network.packet.listener

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import rs.emulator.Repository
import rs.emulator.definitions.enums.EnumDefinition
import rs.emulator.entity.actor.player.messages.chat.ChatMessageType
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.IfButtonMessage
import rs.emulator.network.packet.message.outgoing.*
import rs.emulator.widget.WidgetRegistration

/**
 *
 * @author Chk
 */
class IfButtonListener : GamePacketListener<IfButtonMessage> {

    override fun handle(
        player: Player,
        message: IfButtonMessage
    ) {

        val interfaceId = message.hash shr 16

        val component = message.hash and 0xFFFF

        val option = message.option + 1

        if (player.widgetViewport.isWidgetActive(interfaceId)) {
            WidgetRegistration.fireActionEvent(player, interfaceId, component, message.slot, message.item, option)
                .launchIn(CoroutineScope(Dispatchers.Default))
        } else {
            println("Unhandled button action: [component=[$interfaceId:$component], option=$option, slot=${message.slot}, item=${message.item}]")
        }
    }

}