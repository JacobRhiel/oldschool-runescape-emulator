package rs.emulator.entity.widgets

import rs.emulator.entity.widgets.events.ComponentOpenEvent
import rs.emulator.entity.widgets.widgets.FixedGameFrameWidget

/**
 *
 * @author javatar
 */

class DynamicComponent(id: Int, private val parent: Widget) : Component(id) {

    var component: Component = DEFAULT_COMPONENT

    fun open(component: Component, block: Component.() -> Unit = {}): Component {
        if (this.component !== DEFAULT_COMPONENT) {
            this.component.active = false
            this.component.dispose()
            this.component = component
            this.onNext(ComponentOpenEvent(parent, this, component))
            this.component.active = true
            this.component.block()
        } else {
            this.component = component
            this.onNext(ComponentOpenEvent(parent, this, component))
            this.component.active = true
            this.component.block()
        }
        return component
    }

    companion object {

        val EMPTY = DynamicComponent(-1, FixedGameFrameWidget())

    }

}