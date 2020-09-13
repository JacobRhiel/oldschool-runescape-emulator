package rs.emulator.entity.actor.player

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.collections.varbits.VarbitList
import rs.emulator.entity.actor.IActor
import rs.emulator.entity.actor.combat.CombatFactory
import rs.emulator.entity.actor.effects.AffectHandler
import rs.emulator.entity.actor.player.messages.AbstractMessageHandler
import rs.emulator.entity.details.IPlayerDetails
import rs.emulator.entity.material.containers.ItemContainerManager
import rs.emulator.region.WorldCoordinate
import rs.emulator.widget.WidgetViewport

@ExperimentalCoroutinesApi
interface IPlayer : IActor {

    val widgetViewport: WidgetViewport
    val details: IPlayerDetails
    val containerManager: ItemContainerManager
    val combatFactory: CombatFactory
    val varbits: VarbitList
    override val affectHandler: AffectHandler<IPlayer>
    val playerIndex: Int

    var running: Boolean

    var energy: Int
    var skullIcon: Int
    var prayerIcon: Int
    var isFemale: Boolean

    fun username(): String
    fun displayName(): String

    fun messages(): AbstractMessageHandler

    fun setTeleportCoordinate(coordinate: WorldCoordinate)

    fun update()

    fun save()
    fun logout()

}