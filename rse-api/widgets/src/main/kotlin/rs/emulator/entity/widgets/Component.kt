package rs.emulator.entity.widgets

import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer
import io.reactivex.rxkotlin.ofType
import io.reactivex.subjects.PublishSubject

/**
 *
 * @author javatar
 */

open class Component(
    val id: Int,
    var active: Boolean = false,
    val events: PublishSubject<ComponentEvent> = PublishSubject.create(),
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
) : Observer<ComponentEvent> by events, ObservableSource<ComponentEvent> by events,
    DisposableContainer by compositeDisposable, Disposable by compositeDisposable {

    inline fun <reified E : ComponentEvent> subscribe(noinline onNext: (E) -> Unit): Disposable {
        return this.events.ofType<E>().subscribe(onNext).apply { add(this) }
    }

    companion object {

        val DEFAULT_COMPONENT = Component(-1)

    }

}