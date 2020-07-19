package rs.emulator.network.packet.message.outgoing

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author Chk
 */
class UpdateNpcSyncMessage<P : IPlayer>(val player: P) : GamePacketMessage(83, type = PacketType.VARIABLE_SHORT)
{



}