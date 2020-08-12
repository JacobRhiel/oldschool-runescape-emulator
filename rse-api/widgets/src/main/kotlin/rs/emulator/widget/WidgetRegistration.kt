package rs.emulator.widget

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.widgets.WidgetViewport
import rs.emulator.entity.material.containers.equipment
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
            addActionComponent(54) {
                it.source.widgetViewport.open(149, WidgetViewport.OverlayFrame.TABS)
            }
            addActionComponent(55) {
                it.source.widgetViewport.open(387, WidgetViewport.OverlayFrame.TABS)
            }
        }

        registerWidget(182) {
            addActionComponent(8) {
                it.source.logout()
            }
        }

        /*registerWidget(162) {
            addActionComponent(33) {
                val p = it.source
                if (p.username() == "hunter23912") {
                    p.widgetViewport.open(12, WidgetViewport.OverlayFrame.VIEW_PORT)
                    p.messages().sendClientScript(917, -1, -2)
                    p.messages().sendAccessMask(12, 12, 0, 815, 1312766)
                    p.messages().sendAccessMask(12, 12, 825, 833, 2)
                    p.messages().sendAccessMask(12, 12, 834, 843, 1048576)
                    p.messages().sendAccessMask(12, 10, 10, 10, 1048706)
                    p.messages().sendAccessMask(12, 10, 11, 19, 1179842)
                    p.messages().sendAccessMask(15, 3, 0, 27, 1181694)
                    p.messages().sendAccessMask(15, 12, 0, 27, 1054)
                    p.messages().sendAccessMask(15, 4, 0, 27, 1180674)
                    p.messages().sendAccessMask(12, 46, 1, 816, 2)
                    p.messages().sendAccessMask(12, 49, 0, 3, 2)
                    p.messages().sendClientScript(
                        1495,
                        "Members' capacity: 800<br>+8 for your Authenticator<br>Set a PIN for 8 more.",
                        786439,
                        786546
                    )
                } else {

                }
            }
        }*/

    }

    fun registerWidget(id: Int, widget: Widget) {
        widgets.putIfAbsent(id, widget)
    }

    fun registerWidget(id: Int, build: WidgetBuilder.() -> Unit) {
        val builder = WidgetBuilder(id)
        builder.build()
        registerWidget(id, builder.create())
    }

    fun deregisterWidget(id : Int) {
        widgets.remove(id)
    }

    fun fireActionEvent(
        player: IPlayer,
        interfaceId: Int,
        childId: Int,
        slot: Int,
        itemId: Int,
        option : Int
    ): Flow<ComponentEvent<IPlayer, ActionComponent>> {
        return if (widgets.containsKey(interfaceId)) {
            widgets[interfaceId]?.fireAction(childId, player, slot, itemId, option) ?: emptyFlow()
        } else {
            player.messages().sendChatMessage("Unhandled Widget Action $interfaceId - $childId - $slot - $itemId")
            emptyFlow()
        }
    }

    fun assertWidgetIsRegistered(id : Int) : Boolean = widgets.containsKey(id)

    class WidgetBuilder(val id: Int) {

        private val widget = Widget(id)

        fun addActionComponent(id: Int, action: (ComponentActionEvent) -> Unit) = apply {
            widget[id] = ActionComponent(id, action)
        }

        fun create() = widget

    }

}