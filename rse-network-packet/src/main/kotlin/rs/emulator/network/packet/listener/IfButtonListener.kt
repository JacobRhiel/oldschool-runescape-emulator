package rs.emulator.network.packet.listener

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import rs.emulator.Repository
import rs.emulator.cache.definition.widget.enum.EnumDefinition
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.IfButtonMessage
import rs.emulator.network.packet.message.outgoing.*
import rs.emulator.widget.WidgetRegistration

/**
 *
 * @author Chk
 */
class IfButtonListener : GamePacketListener<IfButtonMessage> {

    private val enum548 = Repository.getDefinition<EnumDefinition>(1129)
    private val enum161 = Repository.getDefinition<EnumDefinition>(1132)

    override fun handle(
        player: Player,
        message: IfButtonMessage
    ) {

        val interfaceId = message.hash shr 16

        val component = message.hash and 0xFFFF

        val option = message.option + 1

        if(interfaceId == 216 && component == 1)
        {

            if(option == 14) {

                player.actions.submit {

                    player.messageHandler.sendChatMessage("first")

                    wait(5)

                    player.messageHandler.sendChatMessage("second", 0)

                }
            }

        }

        if (interfaceId == 378 && component == 78) {

            if (option == 14) {

                player.outgoingPackets.offer(
                    IfCloseSubMessage(
                        165,
                        29
                    )
                )

                player.outgoingPackets.offer(
                    IfOpenOverlayMessage(
                        548
                    )
                )

                player.outgoingPackets.offer(
                    VarpLargeMessage(
                        1042,
                        9888
                    )
                )

                player.outgoingPackets.offer(
                    VarpLargeMessage(
                        1042,
                        9920
                    )
                )

                player.outgoingPackets.offer(
                    VarpLargeMessage(
                        1042,
                        9952
                    )
                )

                player.outgoingPackets.offer(
                    VarpLargeMessage(
                        1042,
                        9984
                    )
                )

                player.outgoingPackets.offer(
                    VarpLargeMessage(
                        1042,
                        10016
                    )
                )

                player.outgoingPackets.offer(
                    VarpLargeMessage(
                        1055,
                        -2147473856
                    )
                )

                player.outgoingPackets.offer(
                    VarpLargeMessage(
                        1042,
                        10176
                    )
                )

                moveSubsToFixedGameFrame(player, enum161, enum548)

                player.outgoingPackets.offer(
                    RunClientScriptMessage(
                        2014,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0
                    )
                )

                player.outgoingPackets.offer(
                    RunClientScriptMessage(
                        2015,
                        0
                    )
                )

                player.outgoingPackets.offer(
                    GameMessageMessage(
                        0,
                        username = player.username(),
                        message = "Welcome to GrinderScape."
                    )
                )
            }

        } else {

            if (player.widgetViewport.isWidgetActive(interfaceId)) {
                WidgetRegistration.fireActionEvent(player, interfaceId, component, message.slot, message.item)
                    .launchIn(CoroutineScope(Dispatchers.Default))
            }

            /*if(player.widgetViewport.rootWidget[interfaceId].component.id == component) {

            }

            player.messagesFromType<IWidgetMessages>()
                .sendChatMessage("Opening ${comp.id} - ${comp.active}")

            if (comp.active) {
                comp.onNext(
                    ComponentClickEvent(
                        comp,
                        option
                    )
                )
            }*/
            println("Unhandled button action: [component=[$interfaceId:$component], option=$option, slot=${message.slot}, item=${message.item}]")
        }
    }

    private fun moveSubsToFixedGameFrame(player: Player, enum: EnumDefinition, other: EnumDefinition) {
        enum.intValues.forEachIndexed { index, it ->
            val hash = enum.keys[index]
            val index548 = other.keys.indexOfFirst { it == hash }
            val hash548 = other.intValues[index548]
            if (hash548 != -1) {
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        hash548 shr 16,
                        hash548 and 255,
                        it shr 16,
                        it and 255
                    )
                )
            }
        }
    }

}