package rs.emulator.widget.components

import rs.emulator.widget.Component
import rs.emulator.widget.components.events.impl.ComponentActionEvent

/**
 *
 * @author javatar
 */

class ActionComponent(id: Int, internal val action: (ComponentActionEvent) -> Unit) : Component(id)