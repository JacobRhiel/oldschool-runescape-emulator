package rs.emulator.entity.actor.player.storage

import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import rs.emulator.containers.Container
import rs.emulator.entity.material.IItem

/**
 *
 * @author javatar
 */

abstract class ItemContainer<Item : IItem>(array: Array<Item>, default: Item) :
    Container<Item, ItemObserver<Item>>(array, default) {

    protected val addPublisher = PublishSubject.create<Item>()
    protected val removePublisher = PublishSubject.create<Item>()

    abstract fun nextSlot(): Int

    fun swap(from : Int, to : Int) {
        val fromItem = this[from]
        val toItem = this[to]

        removePublisher.onNext(fromItem)
        removePublisher.onNext(toItem)
        addPublisher.onNext(fromItem)
        addPublisher.onNext(toItem)

        this[to] = fromItem
        this[from] = toItem

    }

    fun subscribeOnceOnAdd(block: (Item) -> Unit) {
        val observer = ItemObserver<Item> {
            onNext {
                block(it)
                dispose()
            }
        }
        addPublisher.subscribe(observer)
    }

    fun subscribeOnceOnRemove(block: (Item) -> Unit) {
        val observer = ItemObserver<Item> {
            onNext {
                block(it)
                dispose()
            }
        }
        removePublisher.subscribe(observer)
    }

    override fun onAdd(block: ItemObserver<Item>.() -> Unit): Disposable {
        val observer = ItemObserver(block)
        addPublisher.subscribe(observer)
        return observer
    }

    override fun onRemove(block: ItemObserver<Item>.() -> Unit): Disposable {
        val observer = ItemObserver(block)
        removePublisher.subscribe(observer)
        return observer
    }

    infix fun Disposable.and(con: Disposable): Pair<Disposable, Disposable> {
        return this to con
    }
}