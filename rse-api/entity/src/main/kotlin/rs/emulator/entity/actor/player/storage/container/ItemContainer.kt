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
    protected val syncPublisher = PublishSubject.create<ItemContainer<ITEM>>()
    internal var syncSupplier : () -> ContainerObserver<ITEM> = { ContainerObserver() }

    abstract fun nextSlot(): Int

    fun swap(from : Int, to : Int) {
        val fromItem = this[from]
        val toItem = this[to]
        removePublisher.onNext(fromItem.createEvent(from))
        removePublisher.onNext(toItem.createEvent(to))
        addPublisher.onNext(fromItem.createEvent(from))
        addPublisher.onNext(toItem.createEvent(to))
        this[to] = fromItem
        this[from] = toItem
        syncPublisher.onNext(this)
        syncPublisher.onComplete()
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

    infix fun syncBlock(block: ContainerObserver<ITEM>.() -> Unit) {
        syncSupplier = { ContainerObserver<ITEM>().apply(block) }
        syncPublisher.subscribe(syncSupplier())
    }

    fun syncAfter(block : () -> Unit) {
        block()
        forceSync()
    }

    fun forceSync() {
        syncPublisher.onNext(this@ItemContainer)
    }

    fun forceSyncAndComplete() {
        forceSync()
        syncPublisher.onComplete()
    }

    infix fun Disposable.and(con: Disposable): Pair<Disposable, Disposable> {
        return this to con
    }

    fun ITEM.createEvent(slot : Int) : ContainerEvent<ITEM> {
        return ContainerEvent(this, slot)
    }

}