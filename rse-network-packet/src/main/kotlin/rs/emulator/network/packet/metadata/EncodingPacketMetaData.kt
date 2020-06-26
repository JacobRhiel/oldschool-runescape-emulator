package rs.emulator.network.packet.metadata

import rs.emulator.network.packet.ActionType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.PacketType
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
class EncodingPacketMetaData(
    opcode: Int,
    private val encoder: PacketEncoder<out GamePacketMessage>,
    packetType: PacketType = PacketType.FIXED,
    actionType: ActionType = ActionType.NONE,
    length: Int = 0,
    ignore: Boolean = false)
    : PacketMetaData(opcode, packetType, actionType, length, ignore)
{

    fun encode(msg: GamePacketMessage, builder: GamePacketBuilder) = (encoder as PacketEncoder<GamePacketMessage>).encode(msg, builder)

}