package rs.emulator.widgets.events

import rs.emulator.widgets.ComponentEvent
import rs.emulator.widgets.components.Component
import rs.emulator.widgets.components.ContainerComponent

/**
 *
 * @author javatar
 */

class ComponentCloseEvent<C : Component>(val source: ContainerComponent<C>, val canceled: Boolean = false) :
    ComponentEvent