package rs.emulator.widget.dsl

import rs.emulator.widget.components.ActionComponent
import rs.emulator.widget.components.Widget
import rs.emulator.widget.components.events.impl.ComponentActionEvent

/**
 *
 * @author javatar
 */
@WidgetBuilderContext
class WidgetBuilder(val id: Int) {

    val widget: Widget = Widget(id)

    fun components(block: ActionContext.() -> Unit) = ActionContext(this).block()
    fun create() = widget

    @WidgetBuilderContext
    class ActionContext(private val context: WidgetBuilder) {
        fun button(componentId: Int, action: (ComponentActionEvent) -> Unit) {
            context.widget[componentId] = ActionComponent(componentId, action)
        }
    }
}