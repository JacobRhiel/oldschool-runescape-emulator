package rs.emulator.network.packet.listener

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import rs.emulator.entity.material.containers.inventory
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.IfSwapItemMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.entity.items.ItemSwapSlotsActionFactory
import rs.emulator.reactive.launch
import rs.emulator.widget.WidgetViewport

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
class IfSwapItemListener : GamePacketListener<IfSwapItemMessage> {
    override fun handle(
        player: Player,
        message: IfSwapItemMessage
    ) {

        //TODO - validate interface is visible and items exists

        if (player.widgetViewport.isWidgetActive(message.componentHash shr 16)) {
            val view = player.widgetViewport
            if (view.activeWidgets[WidgetViewport.OverlayFrame.TABS] == 149 && (message.componentHash shr 16) == 149) {
                player.inventory().swapSlots(message.fromSlot, message.toSlot).launch()
            }
        }

        if (message.componentHash != -1) {
            flowOf(*RSPluginManager.getExtensions<ItemSwapSlotsActionFactory>().toTypedArray())
                .map {
                    it.registerItemSwapWidget(
                        message.componentHash shr 16,
                        message.componentHash and 255
                    )
                }
                .onEach {
                    it.handleItemSwap(
                        player,
                        message.toSlot,
                        message.fromSlot
                    )
                }
                .launch()
        }

    }
}