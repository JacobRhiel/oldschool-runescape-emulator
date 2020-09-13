package rs.emulator.reactive

import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer
import io.reactivex.subjects.PublishSubject

/**
 *
 * @author javatar
 */

data class ReactiveZone<T>(
    val bottomLeftX: Int,
    val bottomLeftZ: Int,
    val plane: Int = 0,
    val width: Int = 1,
    val height: Int = 1,
    val children: MutableList<ReactiveZone<T>> = mutableListOf(),
    val publisher: PublishSubject<T> = PublishSubject.create(),
    val disposableContainer: CompositeDisposable = CompositeDisposable()
) : Iterable<ReactiveZone<T>> by children,
    Observer<T> by publisher,
    ObservableSource<T> by publisher,
    DisposableContainer by disposableContainer,
    Disposable by disposableContainer {

    fun topX() = bottomLeftX + height
    fun topZ() = bottomLeftZ + width

    fun isWithin(x: Int, z: Int): Boolean {
        return x >= this.bottomLeftX && x <= this.topX() && z >= this.bottomLeftZ && z <= this.topZ()
    }

    inline fun <reified C> subscribe(noinline onNext: (C) -> Unit): Disposable {
        return publisher.ofType(C::class.java).subscribe(onNext).apply { add(this) }
    }
}

fun <T> ReactiveZone<T>.createSubZone(
    bottomLeftX: Int,
    bottomLeftZ: Int,
    plane: Int,
    width: Int,
    height: Int
): ReactiveZone<T> {
    if (bottomLeftX >= this.bottomLeftX && bottomLeftX <= this.topX() && bottomLeftZ >= this.bottomLeftZ && bottomLeftZ <= this.topZ()) {
        val childZone =
            ReactiveZone<T>(bottomLeftX + this.bottomLeftX, bottomLeftZ + this.bottomLeftZ, plane, width, height)
        this.children.add(childZone)
        return childZone
    } else {
        println("Not in parent zone!")
    }
    return this
}