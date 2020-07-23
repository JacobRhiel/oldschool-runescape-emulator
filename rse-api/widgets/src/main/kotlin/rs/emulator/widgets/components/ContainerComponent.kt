package rs.emulator.widgets.components

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer
import rs.emulator.widgets.ComponentEvent

/**
 *
 * @author javatar
 */

open class ContainerComponent<C : Component>(
    id: Int,
    val components: MutableMap<Int, C> = mutableMapOf(),
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
) : Component(id), Disposable by compositeDisposable, DisposableContainer by compositeDisposable {

    operator fun get(component: C): C {
        return components.getOrPut(component.id) { component.also { it.active = true } }
    }

    fun isActive(component: C): Boolean {
        return components[component.id]?.active ?: false
    }

    fun close() {
        this.dispose()
        components.clear()
    }

    inline fun <reified E : ComponentEvent> subscribe(component: C, noinline onNext: (E) -> Unit): Disposable {
        val comp = this[component]
        comp.active = true
        return comp.subscribe(this, onNext)
    }

    companion object {
        val EMPTY =
            ContainerComponent<Component>(
                -1
            )
    }

}