package rs.emulator.entity.widgets.events

import rs.emulator.entity.widgets.Component
import rs.emulator.entity.widgets.ComponentEvent

/**
 *
 * @author javatar
 */

class ComponentClickEvent(override val source: Component, val option: Int) : ComponentEvent