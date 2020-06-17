package rs.emulator.entity.actor.player.storage.container

import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import rs.emulator.containers.Container
import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

abstract class ItemContainer<ITEM : Item>(array: Array<ITEM>, default: ITEM) :
    Container<ITEM, ItemObserver<ITEM>>(array, default) {

    protected val addPublisher = PublishSubject.create<ContainerEvent<ITEM>>()
    protected val removePublisher = PublishSubject.create<ContainerEvent<ITEM>>()

    abstract fun nextSlot(): Int

    fun swap(from : Int, to : Int) {
        val fromItem = this[from]
        val toItem = this[to]

        removePublisher.onNext(fromItem.createEvent())
        removePublisher.onNext(toItem.createEvent())
        addPublisher.onNext(fromItem.createEvent())
        addPublisher.onNext(toItem.createEvent())

        this[to] = fromItem
        this[from] = toItem

    }

    fun subscribeOnceOnAdd(block: (ContainerEvent<ITEM>) -> Unit) {
        val observer = ItemObserver<ITEM> {
            onNext {
                block(it)
                dispose()
            }
        }
        addPublisher.subscribe(observer)
    }

    fun subscribeOnceOnRemove(block: (ContainerEvent<ITEM>) -> Unit) {
        val observer = ItemObserver<ITEM> {
            onNext {
                block(it)
                dispose()
            }
        }
        removePublisher.subscribe(observer)
    }

    override fun onAdd(block: ItemObserver<ITEM>.() -> Unit): Disposable {
        val observer = ItemObserver(block)
        addPublisher.subscribe(observer)
        return observer
    }

    override fun onRemove(block: ItemObserver<ITEM>.() -> Unit): Disposable {
        val observer = ItemObserver(block)
        removePublisher.subscribe(observer)
        return observer
    }

    infix fun Disposable.and(con: Disposable): Pair<Disposable, Disposable> {
        return this to con
    }

    fun ITEM.createEvent() : ContainerEvent<ITEM> {
        return ContainerEvent(this)
    }

}