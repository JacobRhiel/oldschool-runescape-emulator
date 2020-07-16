package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ChatFilterMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.ChatFilterActionFactory

/**
 *
 * @author javatar
 */

class ChatFilterListener : GamePacketListener<ChatFilterMessage> {
    override fun handle(channel: Channel, player: Player, message: ChatFilterMessage) {

        RSPluginManager.getExtensions<ChatFilterActionFactory>()
            .toObservable()
            .concatMap {
                Observable.concat(
                    listOf(
                        Observable.just(
                            it.registerPublicChatFilterAction(
                                message.publicChatMode
                            )
                        ),
                        Observable.just(
                            it.registerPrivateChatFilterAction(
                                message.privateChatMode
                            )
                        ),
                        Observable.just(
                            it.registerTradeChatFilterAction(
                                message.tradeChatMode
                            )
                        )
                    )
                )
            }

    }
}