package rs.emulator.network.packet.atest

import rs.emulator.entity.actor.player.storage.container.ItemContainer
import rs.emulator.entity.material.items.Item
import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author Chk
 */
class UpdateInventoryFullMessage(val container: ItemContainer<*>,
                                 val componentHash: Int,
                                 val containerKey: Int) : GamePacketMessage(34, type = PacketType.VARIABLE_SHORT)
{

    constructor(interfaceId: Int, component: Int, containerKey: Int, container: ItemContainer<*>) : this(
        container,
        (interfaceId shl 16) or component,
        containerKey
    )

    constructor(interfaceId: Int, component: Int, container: ItemContainer<*>) : this(
        container,
        (interfaceId shl 16) or component,
        0
    )

    constructor(containerKey: Int, container: ItemContainer<*>) : this(container, -1, containerKey)

}