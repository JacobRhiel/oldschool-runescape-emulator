package rs.emulator.network.packet.listener

import io.reactivex.rxkotlin.toObservable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import rs.emulator.entity.actor.player.widgets.WidgetViewport
import rs.emulator.entity.material.containers.invalidateState
import rs.emulator.entity.material.containers.inventory
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.IfSwapItemMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.entity.items.ItemSwapSlotsActionFactory
import rs.emulator.utilities.contexts.scopes.ActorScope
import rs.emulator.utilities.koin.get

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
                player.inventory().swapSlots(message.fromSlot, message.toSlot)
                    .launchIn(get<ActorScope>())
            }
        }

        if (message.componentHash != -1) {
            RSPluginManager.getExtensions<ItemSwapSlotsActionFactory>()
                .toObservable()
                .map {
                    it.registerItemSwapWidget(
                        message.componentHash shr 16,
                        message.componentHash and 255
                    )
                }
                .subscribe({
                    it.handleItemSwap(
                        player,
                        message.toSlot,
                        message.fromSlot
                    )
                }, {
                    it.printStackTrace()
                }).dispose()
        }

    }
}