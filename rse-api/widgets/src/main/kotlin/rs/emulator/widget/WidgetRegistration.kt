package rs.emulator.widget

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.widgets.WidgetViewport
import rs.emulator.widget.components.ActionComponent
import rs.emulator.widget.components.Widget
import rs.emulator.widget.components.events.ComponentEvent
import rs.emulator.widget.components.events.impl.ComponentActionEvent

/**
 * Registration of widgets is first come first serve
 * @author javatar
 */

object WidgetRegistration {

    private val widgets = mutableMapOf<Int, Widget>()

    init {
        registerWidget(548) {
            addActionComponent(37) {
                it.source.widgetViewport.open(182, WidgetViewport.OverlayFrame.TABS)
            }
        }

        registerWidget(182) {
            addActionComponent(8) {
                it.source.logout()
            }
        }
    }

    fun registerWidget(id: Int, widget: Widget) {
        widgets.putIfAbsent(id, widget)
    }

    fun registerWidget(id: Int, build: WidgetBuilder.() -> Unit) {
        val builder = WidgetBuilder(id)
        builder.build()
        registerWidget(id, builder.create())
    }

    fun fireActionEvent(
        player: IPlayer,
        interfaceId: Int,
        childId: Int,
        slot: Int,
        itemId: Int
    ): Flow<ComponentEvent<IPlayer, ActionComponent>> {
        return if (widgets.containsKey(interfaceId)) {
            widgets[interfaceId]?.fireAction(childId, player, slot, itemId) ?: emptyFlow()
        } else {
            player.messages().sendChatMessage("Unhandled Widget Action $interfaceId - $childId - $slot - $itemId")
            emptyFlow()
        }
    }

    class WidgetBuilder(val id: Int) {

        private val widget = Widget(id)

        fun addActionComponent(id: Int, action: (ComponentActionEvent) -> Unit) = apply {
            widget[id] = ActionComponent(id, action)
        }

        fun create() = widget

    }

}