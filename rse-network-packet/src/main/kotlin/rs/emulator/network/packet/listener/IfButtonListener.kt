package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.network.packet.atest.*

/**
 *
 * @author Chk
 */
class IfButtonListener : GamePacketListener<IfButtonMessage>
{

    override fun handle(channel: Channel, message: IfButtonMessage)
    {

        val interfaceId = message.hash shr 16

        val component = message.hash and 0xFFFF

        val option = message.option + 1

        if(interfaceId == 378 && component == 78)
        {

            if(option == 58)
            {

                channel.write(IfCloseSubMessage(165, 29))

                channel.write(IfOpenOverlayMessage(548))

                channel.write(VarpLargeMessage(1042, 9888))

                channel.write(VarpLargeMessage(1042, 9920))

                channel.write(VarpLargeMessage(1042, 9952))

                channel.write(VarpLargeMessage(1042, 9984))

                channel.write(VarpLargeMessage(1042, 10016))

                channel.write(VarpLargeMessage(1055, -2147473856))

                channel.write(VarpLargeMessage(1042, 10176))

                channel.write(IfMoveSubMessage(548, 11, 165, 26))
                channel.write(IfMoveSubMessage(548, 14, 165, 4))
                channel.write(IfMoveSubMessage(548, 15, 165, 24))
                channel.write(IfMoveSubMessage(548, 16, 165, 2))
                channel.write(IfMoveSubMessage(548, 17, 165, 5))
                channel.write(IfMoveSubMessage(548, 18, 165, 6))
                channel.write(IfMoveSubMessage(548, 19, 165, 7))
                channel.write(IfMoveSubMessage(548, 20, 165, 25))
                channel.write(IfMoveSubMessage(548, 23, 165, 8))
                channel.write(IfMoveSubMessage(548, 24, 165, 30))
                channel.write(IfMoveSubMessage(548, 26, 165, 1))
                channel.write(IfMoveSubMessage(548, 66, 165, 9))
                channel.write(IfMoveSubMessage(548, 68, 165, 10))
                channel.write(IfMoveSubMessage(548, 69, 165, 11))
                channel.write(IfMoveSubMessage(548, 70, 165, 12))
                channel.write(IfMoveSubMessage(548, 71, 165, 13))
                channel.write(IfMoveSubMessage(548, 72, 165, 14))
                channel.write(IfMoveSubMessage(548, 73, 165, 15))
                channel.write(IfMoveSubMessage(548, 74, 165, 16))
                channel.write(IfMoveSubMessage(548, 75, 165, 17))
                channel.write(IfMoveSubMessage(548, 76, 165, 18))
                channel.write(IfMoveSubMessage(548, 77, 165, 19))
                channel.write(IfMoveSubMessage(548, 78, 165, 20))
                channel.write(IfMoveSubMessage(548, 79, 165, 21))
                channel.write(IfMoveSubMessage(548, 80, 165, 22))
                channel.write(IfMoveSubMessage(548, 81, 165, 23))

                channel.write(RunClientScriptMessage(2014, 0, 0, 0, 0, 0, 0))

                channel.write(RunClientScriptMessage(2015, 0))

            }

        }

        println("Unhandled button action: [component=[$interfaceId:$component], option=$option, slot=${message.slot}, item=${message.item}]")

    }

}