package rs.emulator.entity.actor.player.storage

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.storage.container.ItemContainer
import rs.emulator.entity.material.items.Item
import rs.emulator.entity.material.items.Wearable

/**
 *
 * @author javatar
 */

fun IItemContainerManager<ItemContainer<Item>>.inventory() : ItemContainer<Item> {
    return container(93)
}

fun IItemContainerManager<ItemContainer<Wearable>>.equipment() : ItemContainer<Wearable> {
    return container(94)
}

fun IPlayer.inventory() : ItemContainer<Item> {
    return containerManager().inventory()
}

fun IPlayer.equipment() : ItemContainer<Wearable> {
    return containerManager().container(94)
}