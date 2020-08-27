package rs.emulator.network.packet.message.outgoing

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author Chk
 */
class UpdateFriendListMessage(
    vararg val friends: IPlayer
) : GamePacketMessage(79, type = PacketType.VARIABLE_SHORT)