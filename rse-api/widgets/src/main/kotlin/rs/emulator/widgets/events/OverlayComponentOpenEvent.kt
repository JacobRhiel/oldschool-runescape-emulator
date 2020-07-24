package rs.emulator.widgets.events

import rs.emulator.widgets.ComponentEvent
import rs.emulator.widgets.components.Component
import rs.emulator.widgets.components.ContainerComponent
import rs.emulator.widgets.components.OverlayComponent

/**
 *
 * @author javatar
 */

data class OverlayComponentOpenEvent(
    val parent: OverlayComponent,
    val container: ContainerComponent<ContainerComponent<Component>>
) : ComponentEvent