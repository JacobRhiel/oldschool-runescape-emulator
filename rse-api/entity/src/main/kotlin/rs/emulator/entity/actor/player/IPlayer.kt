package rs.emulator.entity.actor.player

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import rs.emulator.entity.actor.IActor
import rs.emulator.entity.actor.affects.AffectHandler
import rs.emulator.entity.actor.player.messages.AbstractMessageHandler
import rs.emulator.entity.actor.player.widgets.WidgetViewport
import rs.emulator.entity.details.IPlayerDetails
import rs.emulator.entity.material.containers.ItemContainerManager
import rs.emulator.region.coordinate.Coordinate
import rs.emulator.regions.zones.RegionZone

@ExperimentalCoroutinesApi
interface IPlayer : IActor {

    val widgetViewport: WidgetViewport
    val details: IPlayerDetails
    val containerManager: ItemContainerManager
    override val affectHandler: AffectHandler<IPlayer>
    val playerIndex: Int

    var energy: Int
    var skullIcon: Int
    var prayerIcon: Int
    var isFemale: Boolean

    fun username(): String
    fun displayName(): String

    fun messages(): AbstractMessageHandler

    fun setTeleportCoordinate(coordinate: Coordinate)
    fun save()
    fun logout()

}