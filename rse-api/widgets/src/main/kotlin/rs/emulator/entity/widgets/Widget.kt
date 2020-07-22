package rs.emulator.entity.widgets

/**
 *
 * @author javatar
 */

abstract class Widget(val widgetId: Int, private val components: MutableMap<Int, DynamicComponent> = mutableMapOf()) {

    operator fun get(compId: Int): DynamicComponent {
        return components.getOrDefault(compId, DynamicComponent.EMPTY)
    }

    operator fun set(compId: Int, component: DynamicComponent) {
        components[compId] = component
    }

}