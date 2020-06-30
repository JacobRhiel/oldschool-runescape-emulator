package rs.emulator.network.packet.atest

import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author Chk
 */
class UpdateInventoryPartialMessage(val oldItems: HashMap<Int, Int>,
                                    val newItems: HashMap<Int, Int>,
                                    val componentHash: Int,
                                    val containerKey: Int
) : GamePacketMessage(1, type = PacketType.VARIABLE_SHORT)
{

    constructor(interfaceId: Int, component: Int, containerKey: Int, oldItems: HashMap<Int, Int>, newItems: HashMap<Int, Int>) : this(oldItems, newItems, (interfaceId shl 16) or component, containerKey)

    constructor(interfaceId: Int, component: Int, oldItems: HashMap<Int, Int>, newItems: HashMap<Int, Int>) : this(oldItems, newItems, (interfaceId shl 16) or component, 0)

    constructor(containerKey: Int, oldItems: HashMap<Int, Int>, newItems: HashMap<Int, Int>) : this(oldItems, newItems, -1, containerKey)

}