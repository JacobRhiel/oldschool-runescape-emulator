package rs.emulator.entity.widgets.events

import rs.emulator.entity.widgets.Component
import rs.emulator.entity.widgets.ComponentEvent
import rs.emulator.entity.widgets.DynamicComponent
import rs.emulator.entity.widgets.Widget

/**
 *
 * @author javatar
 */

class ComponentOpenEvent(
    val root: Widget,
    val dynamicComponent: DynamicComponent,
    override val source: Component
) : ComponentEvent