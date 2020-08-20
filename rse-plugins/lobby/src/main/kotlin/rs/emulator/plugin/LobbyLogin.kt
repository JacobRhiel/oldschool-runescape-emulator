package rs.emulator.plugin

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.pf4j.Extension
import rs.emulator.definitions.factories.ItemDefinitionFactory
import rs.emulator.definitions.factories.ItemMetaDefinitionFactory
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.widgets.WidgetViewport
import rs.emulator.entity.material.EquipmentSlot
import rs.emulator.entity.material.containers.equipment
import rs.emulator.plugins.extensions.factories.LoginActionFactory
import rs.emulator.plugins.extensions.factories.actions.LoginAction
import rs.emulator.reactive.launch
import rs.emulator.region.WorldCoordinate
import rs.emulator.regions.zones.events.MessageBroadcastZoneEvent
import rs.emulator.utilities.contexts.scopes.ActorScope
import rs.emulator.utilities.koin.get
import rs.emulator.utilities.logger.info
import rs.emulator.world.regions.RegionZoneManager

/**
 *
 * @author javatar
 */
@Extension(plugins = ["LOBBY-PLUGIN"])
class LobbyLogin : LoginActionFactory, LoginAction {
    override fun registerLoginAction(player: IPlayer): LoginAction {
        return this
    }

    override fun onLogin(player: IPlayer) {

        player.messages().sendSmallVarp(18, 1)
        player.messages().sendLargeVarp(20, 59899954)
        player.messages().sendLargeVarp(21, 102084876)
        player.messages().sendLargeVarp(22, 302317568)
        player.messages().sendLargeVarp(23, 6619200)
        player.messages().sendLargeVarp(24, -2146434396)
        player.messages().sendSmallVarp(25, 2)
        player.messages().sendSmallVarp(101, 0)
        player.messages().sendSmallVarp(153, -1)
        player.messages().sendSmallVarp(166, 2)
        player.messages().sendSmallVarp(167, 0)
        player.messages().sendSmallVarp(168, 4)
        player.messages().sendSmallVarp(169, 4)
        player.messages().sendSmallVarp(170, 0)
        player.messages().sendSmallVarp(171, 0)
        player.messages().sendSmallVarp(173, 1)
        player.messages().sendLargeVarp(281, 1000)
        player.messages().sendLargeVarp(284, 60001)
        player.messages().sendSmallVarp(287, 1)
        player.messages().sendLargeVarp(300, 1000)
        player.messages().sendLargeVarp(304, 50000000)
        player.messages().sendLargeVarp(311, 16777216)
        player.messages().sendSmallVarp(346, 2)
        player.messages().sendSmallVarp(406, 20)
        player.messages().sendSmallVarp(427, 1)
        player.messages().sendSmallVarp(447, -1)
        player.messages().sendLargeVarp(449, 2097152)
        player.messages().sendLargeVarp(464, 1073741824)
        player.messages().sendLargeVarp(518, 536870912)
        player.messages().sendSmallVarp(520, 1)
        player.messages().sendLargeVarp(553, -2147483648)
        player.messages().sendSmallVarp(664, -1)
        player.messages().sendLargeVarp(721, 2048)
        player.messages().sendLargeVarp(788, 128)
        player.messages().sendLargeVarp(810, 33554432)
        player.messages().sendSmallVarp(843, 9)
        player.messages().sendSmallVarp(849, -1)
        player.messages().sendSmallVarp(850, -1)
        player.messages().sendSmallVarp(851, -1)
        player.messages().sendSmallVarp(852, -1)
        player.messages().sendSmallVarp(853, -1)
        player.messages().sendSmallVarp(854, -1)
        player.messages().sendSmallVarp(855, -1)
        player.messages().sendSmallVarp(856, -1)
        player.messages().sendLargeVarp(867, 1034)
        player.messages().sendSmallVarp(872, 4)
        player.messages().sendLargeVarp(904, 277)
        player.messages().sendLargeVarp(913, 4194304)
        player.messages().sendLargeVarp(1009, 2097152)
        player.messages().sendLargeVarp(1010, 2048)
        player.messages().sendLargeVarp(1017, 8192)
        player.messages().sendLargeVarp(1021, 2080)
        player.messages().sendLargeVarp(1042, 9856)
        player.messages().sendLargeVarp(1050, 4096)
        player.messages().sendLargeVarp(1052, 1073741824)
        player.messages().sendLargeVarp(1055, -2147473856)
        player.messages().sendSmallVarp(1065, -1)
        player.messages().sendLargeVarp(1067, -784859136)
        player.messages().sendSmallVarp(1074, 0)
        player.messages().sendSmallVarp(1075, -1)
        player.messages().sendSmallVarp(1107, 0)
        player.messages().sendSmallVarp(1141, 32)
        player.messages().sendSmallVarp(1151, -1)
        player.messages().sendLargeVarp(1224, 172395585)
        player.messages().sendLargeVarp(1225, 379887846)
        player.messages().sendLargeVarp(1226, 67207180)
        player.messages().sendSmallVarp(1227, 48)
        player.messages().sendSmallVarp(1306, 0)
        player.messages().sendSmallVarp(1427, -1)
        player.messages().sendLargeVarp(1429, 30720)
        player.messages().sendSmallVarp(1666, 80)
        player.messages().sendSmallVarp(1683, -1)
        player.messages().sendSmallVarp(1706, 2)
        player.messages().sendLargeVarp(1737, -2147483648)
        player.messages().sendSmallVarp(1740, -1)
        player.messages().sendSmallVarp(2657, -1)
        player.messages().sendSmallVarp(2659, -1)
        player.messages().sendSmallVarp(2661, -1)
        player.messages().sendSmallVarp(2663, -1)
        player.messages().sendSmallVarp(2674, -1)
        player.messages().sendLargeVarp(2686, 1000)

        player.messages().sendClientScript(2498, 0, 0, 0)
        player.messages().sendClientScript(2498, 0, 0, 0)
        player.messages().sendClientScript(233, 24772664, 40590, 5, 240, 117, 1936, 0, 1200, -1)
        player.messages().sendClientScript(233, 24772665, 40587, -10, 120, 105, 1747, 0, 1120, 8748)
        player.messages().sendClientScript(3092, 2243, "Subscribe Now")

        player.widgetViewport.openOverlay(WidgetViewport.OverlayMode.FULL_SCREEN)

        player.messages().sendOpenSub(165, 1, 162, 1)
        player.messages().sendOpenSub(165, 2, 651, 1)
        player.messages().sendOpenSub(165, 25, 163, 1)
        player.messages().sendOpenSub(165, 26, 160, 1)
        player.messages().sendOpenSub(165, 6, 122, 1)
        player.widgetViewport.open(378, WidgetViewport.OverlayFrame.VIEW_PORT)

        player.messages().sendClientScript(1080, "https://osrs.game/UpdateBroadcast")

        player.messages().sendClientScript(828, 0)

        player.messages().sendOpenSub(165, 11, 320, 1)
        player.messages().sendOpenSub(165, 12, 629, 1)

        player.messages().sendClientScript(828, 0)

        player.messages().sendOpenSub(629, 33, 76, 1)
        player.messages().sendOpenSub(165, 13, 149, 1)
        player.messages().sendOpenSub(165, 14, 387, 1)
        player.messages().sendOpenSub(165, 15, 541, 1)
        player.messages().sendOpenSub(165, 16, 218, 1)
        player.messages().sendOpenSub(165, 19, 429, 1)

        player.messages().sendClientScript(2498, 0, 0, 0)

        player.messages().sendOpenSub(165, 18, 109, 1)
        player.messages().sendOpenSub(165, 20, 182, 1)
        player.messages().sendOpenSub(165, 21, 261, 1)
        player.messages().sendOpenSub(165, 22, 216, 1)
        player.messages().sendOpenSub(165, 23, 239, 1)
        player.messages().sendOpenSub(165, 17, 7, 1)
        player.messages().sendOpenSub(165, 10, 593, 1)
        player.messages().sendOpenSub(165, 26, 160, 1)

        player.messages().sendDisplayWidgetUpdate()

        player.messages().sendClientScript(2014, 0, 0, 0, 0, 0, 0)

        player.messages().sendClientScript(2015, 0)

        player.energy = 100 //todo: database

        player.skillManager.skillState.onEach { it ->
            it.forEach {
                player.messages().sendSkillUpdate(
                    it.id,
                    it.level,
                    it.experience
                )
            }
        }.launch()

        player.skillManager.invalidateSkills()

        player.messages().setWidgetText(593, 3, "Combat Lvl: 3")


    }
}