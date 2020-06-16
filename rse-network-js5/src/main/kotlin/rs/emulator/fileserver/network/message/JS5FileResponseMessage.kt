package rs.emulator.fileserver.network.message

import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.network.message.NetworkMessage

/**
 *
 * @author Chk
 */
data class JS5FileResponseMessage(val index: Int, val archive: Int, val buffer: BufferedReader) : NetworkMessage
