package rs.emulator.network.world.network.channel.handshake

import rs.emulator.network.protocol.HeaderProtocol

/**
 *
 * @author Chk
 */
enum class HandshakeResponseProtocol(
    private val opcode: Int
)
    : HeaderProtocol
{

    ALLOW_HANDSHAKE(0),

    REJECT_HANDSHAKE(1)

    ;

    companion object
    {

        fun fetchResponse(opcode: Int) = values().firstOrNull { it.opcode == opcode }

    }

}