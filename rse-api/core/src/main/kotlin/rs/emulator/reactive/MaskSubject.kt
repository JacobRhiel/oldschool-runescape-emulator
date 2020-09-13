package rs.emulator.reactive

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.EmptyDisposable
import io.reactivex.internal.queue.SpscLinkedArrayQueue
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.subjects.Subject
import java.util.concurrent.atomic.AtomicReference

/**
 *
 * @author javatar
 */

class MaskSubject<T>(
    private val delayError: Boolean = true,
    @Volatile internal var disposed: Boolean = false,
    @Volatile private var done: Boolean = false,
    internal var enableOperatorFusion: Boolean = false
) : Subject<T>() {

    internal val queue = SpscLinkedArrayQueue<T>(128)
    internal val downstream = AtomicReference<Observer<in T?>>()
    private val onTerminate = AtomicReference<Runnable>()
    internal val wip = MaskQueueDisposable(this)
    internal var error: Throwable? = null


    override fun hasThrowable(): Boolean {
        return done && error != null
    }

    override fun hasObservers(): Boolean {
        return downstream.get() != null
    }

    override fun onComplete() {
        if (done || disposed) {
            return
        }
        done = true

        doTerminate()

        drain()
    }

    override fun onSubscribe(d: Disposable) {
        if (done || disposed) {
            d.dispose()
        }
    }

    override fun onError(e: Throwable) {
        if (done || disposed) {
            RxJavaPlugins.onError(e)
            return
        }
        error = e
        done = true

        doTerminate()

        drain()
    }

    override fun getThrowable(): Throwable? {
        if (done)
            return error
        return null
    }

    override fun subscribeActual(observer: Observer<in T?>) {
        if (hasObservers()) {
            EmptyDisposable.error(IllegalStateException("Only a single observer allowed."), observer)
        } else {
            observer.onSubscribe(wip)
            downstream.lazySet(observer)
            if (disposed) {
                downstream.lazySet(null)
                return
            }
            drain()
        }
    }

    override fun onNext(t: T) {
        if (done || disposed) {
            return
        }
        queue.offer(t)
        drain()
    }

    override fun hasComplete(): Boolean {
        return done && error == null
    }

    private fun drain() {
        if (wip.andIncrement != 0) {
            return
        }
        var a = downstream.get()
        var missed = 1
        while (true) {
            if (a != null) {
                if (enableOperatorFusion) {
                    drainFused(a)
                } else {
                    drainNormal(a)
                }
                return
            }

            missed = wip.addAndGet(-missed)
            if (missed == 0)
                break
            a = downstream.get()
        }
    }

    private fun drainNormal(a: Observer<in T?>) {
        var missed = 1
        val q = queue
        val failFast = !delayError
        var canBeError = true
        while (true) {
            while (true) {
                if (disposed) {
                    downstream.lazySet(null)
                    q.clear()
                    return
                }
                val d = done
                val v = queue.poll()
                val empty = v == null
                if (d) {
                    if (failFast && canBeError) {
                        if (failedFast(q, a)) {
                            return
                        } else {
                            canBeError = false
                        }
                    }

                    if (empty) {
                        errorOrComplete(a)
                    }
                }

                if (empty)
                    break

                a.onNext(v)
            }

            missed = wip.addAndGet(-missed)
            if (missed == 0) {
                break
            }
        }
    }

    private fun drainFused(a: Observer<in T?>) {
        var missed = 1
        val q = queue
        val failFast = !delayError

        while (true) {
            if (disposed) {
                downstream.lazySet(null)
                return
            }
            val done = done
            if (failFast && done) {
                if (failedFast(q, a)) {
                    return
                }
            }

            a.onNext(null)

            if (done) {
                errorOrComplete(a)
            }

            missed = wip.addAndGet(-missed)

            if (missed == 0)
                break
        }
    }

    private fun errorOrComplete(a: Observer<in T?>) {
        downstream.lazySet(null)
        val ex = error
        if (ex != null) {
            a.onError(ex)
        } else {
            a.onComplete()
        }
    }

    private fun failedFast(q: SpscLinkedArrayQueue<T>, a: Observer<in T?>): Boolean {
        val ex = error
        if (ex != null) {
            downstream.lazySet(null)
            q.clear()
            a.onError(ex)
            return true
        }
        return false
    }

    internal fun doTerminate() {
        val r = onTerminate.get()
        if (r != null && onTerminate.compareAndSet(r, null)) {
            r.run()
        }
    }

}