package rs.emulator.network.packet.message.outgoing

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author javatar
 */

data class UpdatePlayerSyncMessage<P : IPlayer>(val player: P) : GamePacketMessage(59, type = PacketType.VARIABLE_SHORT)