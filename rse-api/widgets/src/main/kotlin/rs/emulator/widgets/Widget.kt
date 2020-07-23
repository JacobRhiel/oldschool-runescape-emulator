package rs.emulator.widgets

import rs.emulator.Repository
import rs.emulator.definitions.widget.WidgetDefinition

/**
 *
 * @author javatar
 */

class Widget(
    val id: Int
) {

    val components: Array<Component>

    init {
        val children = Repository.getWidgetDefinition(this.id)
        components = Array(children.size) { Component(this, -1) }
        children.forEach { def ->
            components[def.componentId] = Component(this, def.componentId)
        }
    }

}

fun Component.toDefinition(): WidgetDefinition {
    return Repository.getWidgetDefinition(this.parent.id)[this.id]
}