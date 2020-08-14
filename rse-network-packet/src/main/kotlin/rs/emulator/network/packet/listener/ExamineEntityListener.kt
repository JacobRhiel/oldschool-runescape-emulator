package rs.emulator.network.packet.listener

import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ExamineEntityMessage

/**
 *
 * @author Chk
 */
class ExamineEntityListener : GamePacketListener<ExamineEntityMessage>
{

    override fun handle(
        player: Player,
        message: ExamineEntityMessage
    )
    {

        println("Examine entity type: ${message.entityType}")

    }

}