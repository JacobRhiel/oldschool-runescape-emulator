package rs.emulator.network.world.network.channel.protocol

import rs.emulator.network.protocol.HeaderProtocol

/**
 *
 * @author Chk
 */
enum class WorldConnectionRequestProtocol(
    val opcode: Int
) : HeaderProtocol
{

    CONNECTION(16),

    RECONNECTION(18)

    ;

    companion object
    {

        fun fetchProtocol(opcode: Int) = values().firstOrNull { it.opcode == opcode }

    }

}