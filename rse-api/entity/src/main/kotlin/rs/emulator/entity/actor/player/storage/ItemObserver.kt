package rs.emulator.entity.actor.player.storage

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.CompositeException
import io.reactivex.exceptions.Exceptions
import io.reactivex.internal.disposables.DisposableHelper
import io.reactivex.internal.functions.Functions
import io.reactivex.observers.LambdaConsumerIntrospection
import io.reactivex.plugins.RxJavaPlugins
import rs.emulator.entity.material.IItem
import java.util.concurrent.atomic.AtomicReference

/**
 *
 * @author javatar
 */

class ItemObserver<Item : IItem>(block: ItemObserver<Item>.() -> Unit) :
    AtomicReference<Disposable>(), Observer<Item>, Disposable, LambdaConsumerIntrospection {

    private var onCompleteBlock: () -> Unit = {}
    private var onSubscribeBlock: (Disposable) -> Unit = {}
    private var onNextBlock: (Item) -> Unit = {}
    private var onErrorBlock: (Throwable) -> Unit = {}

    init {
        block(this)
    }

    fun onNext(block: (Item) -> Unit) {
        this.onNextBlock = block
    }

    fun onComplete(block: () -> Unit) {
        this.onCompleteBlock = block
    }

    fun onSubscribe(block: (Disposable) -> Unit) {
        this.onSubscribeBlock = block
    }

    fun onError(block: (Throwable) -> Unit) {
        this.onErrorBlock = block
    }

    override fun onComplete() {
        if (!isDisposed) {
            lazySet(DisposableHelper.DISPOSED)
            try {
                onCompleteBlock()
            } catch (e: Throwable) {
                Exceptions.throwIfFatal(e)
                RxJavaPlugins.onError(e)
            }
        }
    }

    override fun onSubscribe(d: Disposable) {
        if (DisposableHelper.setOnce(this, d)) {
            try {
                onSubscribeBlock(d)
            } catch (ex: Throwable) {
                Exceptions.throwIfFatal(ex)
                d.dispose()
                onError(ex)
            }
        }
    }

    override fun onNext(t: Item) {
        if (!isDisposed) {
            try {
                onNextBlock(t)
            } catch (e: Throwable) {
                Exceptions.throwIfFatal(e)
                get().dispose()
                onError(e)
            }
        }
    }

    override fun onError(e: Throwable) {
        if (!isDisposed) {
            lazySet(DisposableHelper.DISPOSED)
            try {
                onErrorBlock(e)
            } catch (t: Throwable) {
                Exceptions.throwIfFatal(e)
                RxJavaPlugins.onError(CompositeException(e, t))
            }
        } else {
            RxJavaPlugins.onError(e)
        }
    }

    override fun isDisposed(): Boolean {
        return get() === DisposableHelper.DISPOSED
    }

    override fun dispose() {
        DisposableHelper.dispose(this)
    }

    override fun hasCustomOnError(): Boolean {
        return onErrorBlock !== Functions.ON_ERROR_MISSING
    }
}