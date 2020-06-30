package rs.emulator.network.packet.atest

import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author Chk
 */
class UpdateInventoryFullMessage(val items: IntArray,
                                 val componentHash: Int,
                                 val containerKey: Int) : GamePacketMessage(34, type = PacketType.VARIABLE_SHORT)
{

    constructor(interfaceId: Int, component: Int, containerKey: Int, items: IntArray) : this(
        items,
        (interfaceId shl 16) or component,
        containerKey
    )

    constructor(interfaceId: Int, component: Int, items: IntArray) : this(
        items,
        (interfaceId shl 16) or component,
        0
    )

    constructor(containerKey: Int, items: IntArray) : this(items, -1, containerKey)

}