package rs.emulator.widget.components.events.impl

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.widget.components.ActionComponent
import rs.emulator.widget.components.events.ComponentEvent

/**
 *
 * @author javatar
 */

data class ComponentActionEvent(
    override val source: IPlayer,
    override val component: ActionComponent,
    val slot: Int,
    val itemId: Int
) : ComponentEvent<IPlayer, ActionComponent>