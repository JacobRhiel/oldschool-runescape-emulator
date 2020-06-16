package rs.emulator.fileserver.network.message

import rs.emulator.network.message.NetworkMessage

/**
 *
 * @author Chk
 */
data class JS5SetupXORMessage(val key: Int) : NetworkMessage
