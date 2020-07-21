package rs.emulator.entity.actor.player

import rs.emulator.entity.actor.IActor
import rs.emulator.entity.actor.player.messages.AbstractMessageHandler
import rs.emulator.entity.actor.player.storage.IItemContainerManager
import rs.emulator.entity.widgets.WidgetViewport

interface IPlayer : IActor {

    val widgetViewport: WidgetViewport

    fun username(): String
    fun displayName(): String
    fun containerManager(): IItemContainerManager

    fun messages(): AbstractMessageHandler

}