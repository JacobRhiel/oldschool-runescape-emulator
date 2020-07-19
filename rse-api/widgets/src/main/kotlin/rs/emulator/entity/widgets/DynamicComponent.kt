package rs.emulator.entity.widgets

import rs.emulator.entity.widgets.events.ComponentActiveEvent

/**
 *
 * @author javatar
 */

class DynamicComponent(id: Int) : Component(id) {

    var component: Component = DEFAULT_COMPONENT

    fun open(component: Component): Component {
        if (this.component.id != component.id) {
            this.component = component
            this.component.active = true
            this.component.events.onNext(ComponentActiveEvent(component))
        }
        return component
    }

    companion object {

        val EMPTY = DynamicComponent(-1)

    }

}