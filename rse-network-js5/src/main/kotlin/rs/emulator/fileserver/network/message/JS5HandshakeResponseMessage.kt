package rs.emulator.fileserver.network.message

import rs.emulator.fileserver.network.protocol.JS5ResponseProtocol

/**
 *
 * @author Chk
 */
data class JS5HandshakeResponseMessage(val response: JS5ResponseProtocol)