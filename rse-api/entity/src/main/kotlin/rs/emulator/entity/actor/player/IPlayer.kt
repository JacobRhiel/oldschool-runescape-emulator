package rs.emulator.entity.actor.player

import rs.emulator.entity.actor.IActor
import rs.emulator.entity.actor.player.messages.AbstractMessageHandler
import rs.emulator.entity.actor.player.storage.IItemContainerManager
import rs.emulator.entity.actor.player.widgets.WidgetViewport
import rs.emulator.entity.details.IPlayerDetails

interface IPlayer : IActor {

    val widgetViewport: WidgetViewport
    val details: IPlayerDetails

    var energy: Int
    var skullIcon: Int
    var prayerIcon: Int
    var isFemale: Boolean

    fun username(): String
    fun displayName(): String
    fun containerManager(): IItemContainerManager

    fun messages(): AbstractMessageHandler

    fun save()
    fun logout()

}