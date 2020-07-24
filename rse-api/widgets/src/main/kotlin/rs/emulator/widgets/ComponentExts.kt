package rs.emulator.widgets

import io.reactivex.disposables.Disposable
import rs.emulator.widgets.components.ContainerComponent
import rs.emulator.widgets.components.Widget
import rs.emulator.widgets.events.ComponentOpenEvent

/**
 *
 * @author javatar
 */

fun ContainerComponent<Widget>.subscribe(onNext: (ComponentOpenEvent) -> Unit): Disposable {
    return this.events.ofType(ComponentOpenEvent::class.java).subscribe(onNext).also { this.add(it) }
}