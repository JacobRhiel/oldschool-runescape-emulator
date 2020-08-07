package rs.emulator.network.packet.message.outgoing

import rs.emulator.entity.material.containers.ItemContainer
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