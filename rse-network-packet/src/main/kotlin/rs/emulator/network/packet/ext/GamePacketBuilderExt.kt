package rs.emulator.network.packet.ext

import com.google.common.base.Preconditions
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.GamePacket
import rs.emulator.packet.api.PacketType

/**
 *
 * @author Chk
 */
/**
 * Creates a [GamePacketMessage] based on the current contents of this builder.
 *
 * @return The [GamePacketMessage].
 * @throws IllegalStateException If the builder is not in byte access mode, or if the packet is raw.
 */
fun GamePacketBuilder.toGamePacketMessage(): GamePacketMessage
{

    Preconditions.checkState(packetType != PacketType.RAW, "Raw packets cannot be converted to a game packet.")

    Preconditions.checkState(mode === rs.emulator.buffer.access.AccessMode.BYTE_ACCESS, "Must be in byte access mode to convert to a packet.")

    return GamePacketMessage(
        opcode,
        actionType,
        packetType,
        payload = buffer
    )

}

fun GamePacketBuilder.toGamePacket(): GamePacket
{

    Preconditions.checkState(packetType != PacketType.RAW, "Raw packets cannot be converted to a game packet.")

    Preconditions.checkState(mode === rs.emulator.buffer.access.AccessMode.BYTE_ACCESS, "Must be in byte access mode to convert to a packet.")

    return GamePacket(
        opcode,
        actionType,
        packetType,
        buffer
    )

}