package rs.emulator.entity.actor.player.storage

import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.toObservable
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.storage.container.ItemContainer
import rs.emulator.entity.material.items.Item
import rs.emulator.entity.material.items.Wearable
import rs.emulator.entity.material.items.WearableEffect

/**
 *
 * @author javatar
 */

fun IItemContainerManager.inventory(): ItemContainer<Item> {
    return container(93)
}

fun IItemContainerManager.equipment(): ItemContainer<Wearable> {
    return container(94)
}

inline fun <reified E : WearableEffect> ItemContainer<Wearable>.forEachEffect(noinline block: E.() -> Unit) {
    this.toObservable().ofType<WearableEffect>().subscribe { block(it as E) }
}

fun IPlayer.inventory(): ItemContainer<Item> {
    return containerManager().inventory()
}

fun IPlayer.equipment(): ItemContainer<Wearable> {
    return containerManager().container(94)
}