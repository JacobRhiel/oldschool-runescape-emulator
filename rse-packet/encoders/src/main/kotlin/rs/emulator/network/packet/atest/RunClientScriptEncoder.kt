package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.utilities.logger.logger

/**
 *
 * @author Chk
 */
class RunClientScriptEncoder : PacketEncoder<RunClientScriptMessage>()
{

    override fun encode(message: RunClientScriptMessage, player: Player, builder: GamePacketBuilder)
    {

        val types = CharArray(message.args.size + 1)

        for (i in message.args.indices)
            types[i] = if (message.args[i] is String) 's' else 'i'

        types[message.args.size] = 0.toChar()

        val typeBytes = String(types).toByteArray()

        val args = mutableListOf<Byte>()
        for (i in message.args.size - 1 downTo 0) {
            val value = message.args[i]
            when (value) {
                is String -> {
                    value.toByteArray().forEach { args.add(it) }
                    args.add(0) // Terminator
                }
                is Number -> {
                    args.add((value.toInt() shr 24).toByte())
                    args.add((value.toInt() shr 16).toByte())
                    args.add((value.toInt() shr 8).toByte())
                    args.add(value.toByte())
                }
                else -> logger().error("Invalid argument type {} for script {}.", value::class.java, message.id)
            }
        }

        val argBytes = args.toByteArray()

        builder.putBytes(typeBytes)

        builder.putBytes(argBytes)

        builder.put(DataType.INT, message.id)

    }

}