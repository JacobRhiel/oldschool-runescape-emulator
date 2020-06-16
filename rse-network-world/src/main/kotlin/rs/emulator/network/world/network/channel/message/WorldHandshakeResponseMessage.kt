package rs.emulator.network.world.network.channel.message

import rs.emulator.network.world.network.channel.protocol.WorldConnectionResponseProtocol

/**
 *
 * @author Chk
 */
data class WorldHandshakeResponseMessage(val response: WorldConnectionResponseProtocol)