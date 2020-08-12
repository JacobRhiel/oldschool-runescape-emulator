package rs.emulator.network.packet.listener

import io.reactivex.rxkotlin.toObservable
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.messages.IWidgetMessages
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.PlayerActionMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.entity.actions.PlayerActionFactory
import rs.emulator.world.GameWorld

/**
 *
 * @author javatar
 */

class PlayerActionListener : GamePacketListener<PlayerActionMessage> {
    override fun handle(
        player: Player,
        message: PlayerActionMessage
    ) {
        if (message.option != -1 && message.playerIndex != -1) {
            val otherPlayer: IPlayer? = GameWorld.players[message.playerIndex]
            if (otherPlayer != null) {
                RSPluginManager.getExtensions<PlayerActionFactory>()
                    .toObservable()
                    .map {
                        it.registerPlayerActions(
                            message.playerIndex,
                            message.option,
                            message.controlPressed
                        )
                    }
                    .subscribe({
                        it.handlePlayerAction(player, otherPlayer, message.option)
                    }, {
                        player.messages().ofType<IWidgetMessages>()
                            .sendChatMessage("Nothing interesting happens.", 0)
                    })
                    .dispose()
            }
        }
    }
}