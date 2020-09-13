package rs.emulator.widget.containers

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.widget.components.ActionComponent
import rs.emulator.widget.components.events.ComponentEvent
import rs.emulator.widget.components.events.impl.ComponentActionEvent

/**
 *
 * @author javatar
 */

class Widget(id: Int) : ContainerComponent<ActionComponent>(id) {

    fun fireAction(
        id: Int,
        player: IPlayer,
        slot: Int = -1,
        itemId: Int = -1,
        option : Int = 78
    ): Flow<ComponentEvent<IPlayer, ActionComponent>> =
        flow { emit(ComponentActionEvent(player, this@Widget[id], slot, itemId, option)) }.onEach { it.component.action(it) }

}