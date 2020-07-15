package rs.emulator.network.packet.message.outgoing

import rs.emulator.entity.actor.player.storage.container.ItemContainer
import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author Chk
 */
class UpdateInventoryPartialMessage(val container : ItemContainer<*>,
                                    val componentHash: Int,
                                    val containerKey: Int
) : GamePacketMessage(1, type = PacketType.VARIABLE_SHORT)