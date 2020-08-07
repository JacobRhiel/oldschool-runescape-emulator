package rs.emulator.entity.actor.player

import rs.emulator.entity.actor.IActor
import rs.emulator.entity.actor.player.messages.AbstractMessageHandler
import rs.emulator.entity.actor.player.widgets.WidgetViewport
import rs.emulator.entity.details.IPlayerDetails
import rs.emulator.entity.material.containers.ItemContainerManager

interface IPlayer : IActor {

    val widgetViewport: WidgetViewport
    val details: IPlayerDetails
    val containerManager: ItemContainerManager

    var energy: Int
    var skullIcon: Int
    var prayerIcon: Int
    var isFemale: Boolean

    fun username(): String
    fun displayName(): String

    fun messages(): AbstractMessageHandler

    fun save()
    fun logout()

}