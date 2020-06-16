package rs.emulator.network.world.network.channel.handshake.message

import rs.emulator.network.message.NetworkMessage
import rs.emulator.network.world.network.channel.handshake.HandshakeRequestProtocol


/**
 *
 * @author Chk
 */
data class RequestHandshakePipelineMessage(val request: HandshakeRequestProtocol) : NetworkMessage
