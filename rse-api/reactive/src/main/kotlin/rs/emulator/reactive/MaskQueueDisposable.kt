package rs.emulator.reactive

import io.reactivex.internal.fuseable.QueueFuseable
import io.reactivex.internal.observers.BasicIntQueueDisposable

/**
 *
 * @author javatar
 */

class MaskQueueDisposable<T>(val mask: MaskSubject<T>) : BasicIntQueueDisposable<T>() {
    override fun isEmpty(): Boolean {
        return mask.queue.isEmpty
    }

    override fun toByte(): Byte = get().toByte()

    override fun requestFusion(mode: Int): Int {
        if ((mode and ASYNC) != 0) {
            mask.enableOperatorFusion = true
            return QueueFuseable.ASYNC
        }
        return QueueFuseable.NONE
    }

    override fun toChar(): Char = get().toChar()

    override fun toShort(): Short = get().toShort()

    override fun dispose() {
        if (!mask.disposed) {
            mask.disposed = true

            mask.doTerminate()

            mask.downstream.lazySet(null)
            if (mask.wip.andIncrement == 0) {
                mask.downstream.lazySet(null)
                if (!mask.enableOperatorFusion) {
                    mask.queue.clear()
                }
            }
        }
    }

    override fun isDisposed(): Boolean {
        return mask.disposed
    }

    override fun clear() {
        mask.queue.clear()
    }

    override fun poll(): T? {
        return mask.queue.poll()
    }
}