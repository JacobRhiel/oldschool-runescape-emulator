package rs.emulator.widgets.components

import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer
import io.reactivex.subjects.PublishSubject
import rs.emulator.widgets.ComponentEvent

/**
 *
 * @author javatar
 */

open class Component(
    val id: Int,
    var active: Boolean = false,
    val events: PublishSubject<ComponentEvent> = PublishSubject.create()
) {

    inline fun <reified E : ComponentEvent> subscribe(
        disposable: DisposableContainer,
        noinline onNext: (E) -> Unit
    ): Disposable {
        return events.ofType(E::class.java).subscribe(onNext).also { disposable.add(it) }
    }
}