package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.IfButtonMessage
import rs.emulator.network.packet.message.outgoing.*

/**
 *
 * @author Chk
 */
class IfButtonListener : GamePacketListener<IfButtonMessage>
{

    override fun handle(channel: Channel, player: Player, message: IfButtonMessage)
    {

        val interfaceId = message.hash shr 16

        val component = message.hash and 0xFFFF

        val option = message.option + 1

        /*val interactionExtensions = RSPluginManager.getExtensions(WidgetInteractionExtensionPoint::class.java)

        interactionExtensions.forEach {
            it.onClick(player, message.hash, message.option, message.slot, message.item)
        }*/

        if(interfaceId == 378 && component == 78)
        {

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

                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        27,
                        165,
                        1
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        23,
                        165,
                        8
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        14,
                        165,
                        4
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        17,
                        165,
                        5
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        67,
                        165,
                        9
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        69,
                        165,
                        10
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        70,
                        165,
                        11
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        71,
                        165,
                        12
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        72,
                        165,
                        13
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        73,
                        165,
                        14
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        74,
                        165,
                        15
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        75,
                        165,
                        16
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        76,
                        165,
                        17
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        77,
                        165,
                        18
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        78,
                        165,
                        19
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        79,
                        165,
                        20
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        80,
                        165,
                        21
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        81,
                        165,
                        22
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        82,
                        165,
                        23
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        15,
                        165,
                        24
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        20,
                        165,
                        25
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        11,
                        165,
                        26
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        18,
                        165,
                        6
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        19,
                        165,
                        7
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        24,
                        165,
                        30
                    )
                )
                player.outgoingPackets.offer(
                    IfMoveSubMessage(
                        548,
                        16,
                        165,
                        2
                    )
                )

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


                //player.outgoingPackets.offer(UpdateInventoryPartialMessage(interfaceId = 149, component = 0, containerKey = 93, oldItems = hashMapOf(Pair(4151, 1)), newItems = hashMapOf(Pair(1038, 1))))

            }

        }

        println("Unhandled button action: [component=[$interfaceId:$component], option=$option, slot=${message.slot}, item=${message.item}]")

    }

}