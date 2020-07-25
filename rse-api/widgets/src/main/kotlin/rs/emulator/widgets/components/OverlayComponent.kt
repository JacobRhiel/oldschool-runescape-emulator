package rs.emulator.widgets.components

import io.reactivex.disposables.Disposable
import rs.emulator.widgets.ComponentEvent

/**
 *
 * @author javatar
 */

class OverlayComponent(id: Int) : ContainerComponent<ContainerComponent<Widget>>(id) {

    inline fun <reified E : ComponentEvent> subscribeTo(
        component: Component,
        noinline onNext: (E) -> Unit
    ): Disposable {
        return this.events.ofType(E::class.java).subscribe(onNext).also { this.add(it) }
    }

}