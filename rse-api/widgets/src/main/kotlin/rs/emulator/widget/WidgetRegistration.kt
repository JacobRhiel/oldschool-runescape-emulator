package rs.emulator.widget

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import rs.emulator.Repository
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

    private val widgets = mutableMapOf<Int, RegisteredWidget>()

    init {
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

    fun registerWidget(id: Int, widget: Widget, pluginName : String) {
        widgets.putIfAbsent(id, RegisteredWidget(widget, pluginName))
    }

    fun registerWidget(id: Int, pluginName: String, build: WidgetBuilder.() -> Unit) {
        if(widgets.containsKey(id)) {
            val registeredWidget = widgets[id]!!
            throw Error("Widget $id already registered from plugin ${registeredWidget.plugin}.")
        } else {
            val builder = WidgetBuilder(id)
            builder.build()
            registerWidget(id, builder.create(), pluginName)
        }
    }

    /**
     * This function should NOT be used, use at your own risk.
     * Please favour registeredWidget over this function.
     */
    fun overrideWidgetRegistration(id: Int, pluginName: String, build: WidgetBuilder.() -> Unit) {
        val builder = WidgetBuilder(id)
        builder.build()
        widgets[id] = RegisteredWidget(builder.create(), pluginName)
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
        option : Int,
        opcode: Int
    ): Flow<ComponentEvent<IPlayer, ActionComponent>>
    {

        val def = Repository.getWidgetDefinition(interfaceId)

        for (wdef in def)
        {

            if (wdef.id == childId)
            {
                var index = wdef.actions.indexOfFirst { it == "Close" }
                if(index == -1)
                    index = wdef.configActions.indexOfFirst { it == "Close" }
                if(index != -1 && opcode == actionOpcodes[index])
                    player.widgetViewport.close(interfaceId)
                break
            }

        }
        return if (widgets.containsKey(interfaceId))
            widgets[interfaceId]?.widget?.fireAction(childId, player, slot, itemId, option) ?: emptyFlow()
        else
        {
            player.messages().sendChatMessage("Unhandled Widget Action $interfaceId - $childId - $slot - $itemId")
            emptyFlow()
        }
    }

    private val actionOpcodes = intArrayOf(13, 77, 34, 91)

    fun assertWidgetIsRegistered(id : Int) : Boolean = widgets.containsKey(id)

    class WidgetBuilder(val id: Int) {

        private val widget = Widget(id)

        fun addActionComponent(id: Int, action: (ComponentActionEvent) -> Unit) = apply {
            widget[id] = ActionComponent(id, action)
        }

        fun create() = widget

    }

    data class RegisteredWidget(val widget : Widget, val plugin : String)

}