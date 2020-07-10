package rs.emulator.entity.player

import io.netty.channel.Channel
import rs.emulator.engine.service.event.bus.CoreEventBus
import rs.emulator.entity.actor.Actor
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.storage.IItemContainerManager
import rs.emulator.entity.actor.player.storage.container.ItemContainer
import rs.emulator.entity.material.items.Item
import rs.emulator.entity.player.storage.ItemContainerManager
import rs.emulator.entity.player.storage.containers.Equipment
import rs.emulator.entity.player.storage.containers.Inventory
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.entity.player.update.sync.SyncInformation
import rs.emulator.entity.player.viewport.Viewport
import rs.emulator.network.packet.atest.*
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.ContainerRegistrationException
import rs.emulator.plugins.extensions.factories.ItemContainerFactory

class Player(val channel: Channel) : Actor(), IPlayer {

    val viewport = Viewport(this)

    val syncInfo = SyncInformation().apply { this.addMaskFlag(PlayerUpdateFlag.APPEARANCE) }

    fun onLogin() {

        channel.write(RebuildRegionMessage(true, 1, x = 3222, z = 3218, tileHash = -1))

        channel.write(VarpSmallMessage(18, 1))
        channel.write(VarpLargeMessage(20, 59899954))
        channel.write(VarpLargeMessage(21, 102084876))
        channel.write(VarpLargeMessage(22, 302317568))
        channel.write(VarpLargeMessage(23, 6619200))
        channel.write(VarpLargeMessage(24, -2146434396))
        channel.write(VarpSmallMessage(25, 2))
        channel.write(VarpSmallMessage(101, 0))
        channel.write(VarpSmallMessage(153, -1))
        channel.write(VarpSmallMessage(166, 2))
        channel.write(VarpSmallMessage(167, 0))
        channel.write(VarpSmallMessage(168, 4))
        channel.write(VarpSmallMessage(169, 4))
        channel.write(VarpSmallMessage(170, 0))
        channel.write(VarpSmallMessage(171, 0))
        channel.write(VarpSmallMessage(173, 1))
        channel.write(VarpLargeMessage(281, 1000))
        channel.write(VarpLargeMessage(284, 60001))
        channel.write(VarpSmallMessage(287, 1))
        channel.write(VarpLargeMessage(300, 1000))
        channel.write(VarpLargeMessage(304, 50000000))
        channel.write(VarpLargeMessage(311, 16777216))
        channel.write(VarpSmallMessage(346, 2))
        channel.write(VarpSmallMessage(406, 20))
        channel.write(VarpSmallMessage(427, 1))
        channel.write(VarpSmallMessage(447, -1))
        channel.write(VarpLargeMessage(449, 2097152))
        channel.write(VarpLargeMessage(464, 1073741824))
        channel.write(VarpLargeMessage(518, 536870912))
        channel.write(VarpSmallMessage(520, 1))
        channel.write(VarpLargeMessage(553, -2147483648))
        channel.write(VarpSmallMessage(664, -1))
        channel.write(VarpLargeMessage(721, 2048))
        channel.write(VarpLargeMessage(788, 128))
        channel.write(VarpLargeMessage(810, 33554432))
        channel.write(VarpSmallMessage(843, 9))
        channel.write(VarpSmallMessage(849, -1))
        channel.write(VarpSmallMessage(850, -1))
        channel.write(VarpSmallMessage(851, -1))
        channel.write(VarpSmallMessage(852, -1))
        channel.write(VarpSmallMessage(853, -1))
        channel.write(VarpSmallMessage(854, -1))
        channel.write(VarpSmallMessage(855, -1))
        channel.write(VarpSmallMessage(856, -1))
        channel.write(VarpLargeMessage(867, 1034))
        channel.write(VarpSmallMessage(872, 4))
        channel.write(VarpLargeMessage(904, 277))
        channel.write(VarpLargeMessage(913, 4194304))
        channel.write(VarpLargeMessage(1009, 2097152))
        channel.write(VarpLargeMessage(1010, 2048))
        channel.write(VarpLargeMessage(1017, 8192))
        channel.write(VarpLargeMessage(1021, 2080))
        channel.write(VarpLargeMessage(1042, 9856))
        channel.write(VarpLargeMessage(1050, 4096))
        channel.write(VarpLargeMessage(1052, 1073741824))
        channel.write(VarpLargeMessage(1055, -2147473856))
        channel.write(VarpSmallMessage(1065, -1))
        channel.write(VarpLargeMessage(1067, -784859136))
        channel.write(VarpSmallMessage(1074, 0))
        channel.write(VarpSmallMessage(1075, -1))
        channel.write(VarpSmallMessage(1107, 0))
        channel.write(VarpSmallMessage(1141, 32))
        channel.write(VarpSmallMessage(1151, -1))
        channel.write(VarpLargeMessage(1224, 172395585))
        channel.write(VarpLargeMessage(1225, 379887846))
        channel.write(VarpLargeMessage(1226, 67207180))
        channel.write(VarpSmallMessage(1227, 48))
        channel.write(VarpSmallMessage(1306, 0))
        channel.write(VarpSmallMessage(1427, -1))
        channel.write(VarpLargeMessage(1429, 30720))
        channel.write(VarpSmallMessage(1666, 80))
        channel.write(VarpSmallMessage(1683, -1))
        channel.write(VarpSmallMessage(1706, 2))
        channel.write(VarpLargeMessage(1737, -2147483648))
        channel.write(VarpSmallMessage(1740, -1))
        channel.write(VarpSmallMessage(2657, -1))
        channel.write(VarpSmallMessage(2659, -1))
        channel.write(VarpSmallMessage(2661, -1))
        channel.write(VarpSmallMessage(2663, -1))
        channel.write(VarpSmallMessage(2674, -1))
        channel.write(VarpLargeMessage(2686, 1000))

        channel.write(RunClientScriptMessage(2498, 0, 0, 0))
        channel.write(RunClientScriptMessage(2498, 0, 0, 0))
        channel.write(RunClientScriptMessage(233, 24772664, 40590, 5, 240, 117, 1936, 0, 1200, -1))
        channel.write(RunClientScriptMessage(233, 24772665, 40587, -10, 120, 105, 1747, 0, 1120, 8748))
        channel.write(RunClientScriptMessage(3092, 2243, "Subscribe Now"))

        channel.write(IfOpenOverlayMessage(165))

        channel.write(IfOpenSubMessage(165, 1, 162, 1))
        channel.write(IfOpenSubMessage(165, 2, 651, 1))
        channel.write(IfOpenSubMessage(165, 25, 163, 1))
        channel.write(IfOpenSubMessage(165, 26, 160, 1))
        channel.write(IfOpenSubMessage(165, 6, 122, 1))
        channel.write(IfOpenSubMessage(165, 29, 378, 0))

        channel.write(RunClientScriptMessage(1080, "https://osrs.game/UpdateBroadcast"))

        channel.write(RunClientScriptMessage(828, 0))

        channel.write(IfOpenSubMessage(165, 11, 320, 1))
        channel.write(IfOpenSubMessage(165, 12, 629, 1))

        channel.write(RunClientScriptMessage(828, 0))

        channel.write(IfOpenSubMessage(629, 33, 76, 1))
        channel.write(IfOpenSubMessage(165, 13, 149, 1))
        channel.write(IfOpenSubMessage(165, 14, 387, 1))
        channel.write(IfOpenSubMessage(165, 15, 541, 1))
        channel.write(IfOpenSubMessage(165, 16, 218, 1))
        channel.write(IfOpenSubMessage(165, 19, 429, 1))

        channel.write(RunClientScriptMessage(2498, 0, 0, 0))

        channel.write(IfOpenSubMessage(165, 18, 109, 1))
        channel.write(IfOpenSubMessage(165, 20, 182, 1))
        channel.write(IfOpenSubMessage(165, 21, 261, 1))
        channel.write(IfOpenSubMessage(165, 22, 216, 1))
        channel.write(IfOpenSubMessage(165, 23, 239, 1))
        channel.write(IfOpenSubMessage(165, 17, 7, 1))
        channel.write(IfOpenSubMessage(165, 10, 593, 1))
        channel.write(IfOpenSubMessage(165, 26, 160, 1))

        channel.write(UpdateDisplayWidgetsMessage())

        channel.write(RunClientScriptMessage(2014, 0, 0, 0, 0, 0, 0))

        channel.write(RunClientScriptMessage(2015, 0))

        containerManager().register(93, Inventory()) {
            syncBlock {
                onNext {
                    sendItemContainerFull(149, 0, 93, this@register)
                }
            }
        }


        if (channel.isActive)
            channel.flush()

    }

    private val itemContainerManager = ItemContainerManager().apply {
        RSPluginManager.getExtensions(ItemContainerFactory::class.java).forEach {
            if (this.containers.containsKey(it.containerKey)) {
                throw ContainerRegistrationException(it.containerKey)
            } else {
                this.containers[it.containerKey] = it.registerItemContainer(this@Player)
            }
        }
    }

    override fun username(): String = "Javatar"

    override fun displayName(): String = "Javatar"

    override fun containerManager(): IItemContainerManager {
        return itemContainerManager
    }

    override fun sendSmallVarp(id: Int, value: Int) {
        channel.write(VarpSmallMessage(id, value))
    }

    override fun sendLargeVarp(id: Int, value: Int) {
        channel.write(VarpLargeMessage(id, value))
    }

    override fun sendClientScript(scriptId: Int, vararg params: Any) {
        channel.write(RunClientScriptMessage(scriptId, params))
    }

    override fun sendOpenOverlay(id: Int) {
        channel.write(IfOpenOverlayMessage(id))
    }

    override fun sendOpenSub(parentId: Int, childId: Int, component: Int, interType: Int) {
        channel.write(IfOpenSubMessage(parentId, childId, component, interType))
    }

    override fun sendDisplayWidgetUpdate() {
        channel.write(UpdateDisplayWidgetsMessage())
    }

    override fun sendChatMessage(message: String, messageType: Int) {
        channel.write(GameMessageMessage(messageType, displayName(), message))
    }

    override fun sendItemContainerFull(
        interfaceId: Int,
        component: Int,
        containerKey: Int,
        container: ItemContainer<*>
    ) {
        channel.write(UpdateInventoryFullMessage(interfaceId, component, containerKey, container))
    }

    override fun sendItemContainerPartial(
        interfaceId: Int,
        component: Int,
        containerKey: Int,
        container: ItemContainer<*>
    ) {
        channel.write(
            UpdateInventoryPartialMessage(
                container,
                interfaceId shl 16 and component,
                containerKey
            )
        )
    }
}