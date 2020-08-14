package rs.emulator.network.packet.listener

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import rs.emulator.entity.material.containers.inventory
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ChatFilterMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.CameraRotationActionFactory
import rs.emulator.plugins.extensions.factories.entity.chat.ChatFilterActionFactory
import rs.emulator.utilities.contexts.scopes.ActorScope
import rs.emulator.utilities.koin.get

/**
 *
 * @author javatar
 */

class ChatFilterListener : GamePacketListener<ChatFilterMessage> {
    override fun handle(
        player: Player,
        message: ChatFilterMessage
    ) {

        flowOf(*RSPluginManager.getExtensions<ChatFilterActionFactory>().toTypedArray())
            .map { it.registerPublicChatFilter(message.publicChatMode, message.privateChatMode, message.tradeChatMode) }
            .onEach { it.handleChatFilterSettings(player, message.publicChatMode, message.privateChatMode, message.tradeChatMode) }
            .launchIn(get<ActorScope>())

    }
}