package rs.emulator.widgets.events

import rs.emulator.widgets.ComponentEvent
import rs.emulator.widgets.components.Component
import rs.emulator.widgets.components.Widget

/**
 *
 * @author javatar
 */

class ComponentClickEvent(val widget: Widget, comp: Component, slot: Int = -1, item: Int = -1) : ComponentEvent