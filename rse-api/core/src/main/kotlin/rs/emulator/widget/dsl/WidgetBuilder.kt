package rs.emulator.widget.dsl

import rs.emulator.widget.components.ActionComponent
import rs.emulator.widget.containers.Widget
import rs.emulator.widget.components.events.impl.ComponentActionEvent

/**
 *
 * @author javatar
 */
@WidgetBuilderContext
class WidgetBuilder(val id: Int) {

    val widget: Widget = Widget(id)

    fun components(block: ActionContext.() -> Unit) = ActionContext().block()
    fun create() = widget

    @WidgetBuilderContext
    inner class ActionContext {
        fun button(componentId: Int, action: (ComponentActionEvent) -> Unit) {
            this@WidgetBuilder.widget[componentId] = ActionComponent(componentId, action)
        }
    }
}