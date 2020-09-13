package rs.emulator.widget.containers

import rs.emulator.widget.components.Component

/**
 *
 * @author javatar
 */

abstract class ContainerComponent<C : Component>(id: Int, private val components: MutableMap<Int, C> = mutableMapOf()) :
    Component(id) {

    operator fun get(id: Int): C {
        return components[id] ?: throw Error("Component $id does not exist in component container ${this.id}")
    }

    operator fun set(id: Int, component: C) {
        components[id] = component
    }

}