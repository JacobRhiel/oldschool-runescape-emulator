package rs.emulator.entity.widgets

import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.ofType

/**
 *
 * @author javatar
 */

inline fun <reified E : WidgetEvent> Component.subscribe(noinline onNext: (E) -> Unit): Disposable {
    return this.events.ofType<E>().subscribe(onNext)
}
