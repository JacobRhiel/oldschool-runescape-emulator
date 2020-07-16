package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import io.reactivex.rxkotlin.toObservable
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.IfSwapItemMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.entity.items.ItemSwapSlotsActionFactory

/**
 *
 * @author javatar
 */

class IfSwapItemListener : GamePacketListener<IfSwapItemMessage> {
    override fun handle(channel: Channel, player: Player, message: IfSwapItemMessage) {

        //TODO - validate interface is visible and items exists

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