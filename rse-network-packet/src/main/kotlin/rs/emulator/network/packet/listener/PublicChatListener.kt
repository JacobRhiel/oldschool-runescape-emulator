package rs.emulator.network.packet.listener

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.rxkotlin.zipWith
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.encryption.huffman.HuffmanCodec
import rs.emulator.entity.player.Player
import rs.emulator.entity.player.chat.PublicChatText
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.network.packet.message.incoming.PublicChatMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.actions.chat.ChatTextFilterAction
import rs.emulator.plugins.extensions.factories.entity.chat.ChatTextFilterFactory
import rs.emulator.plugins.extensions.factories.entity.chat.DefaultChatFilterFactory

/**
 *
 * @author Chk
 */
class PublicChatListener : GamePacketListener<PublicChatMessage>, KoinComponent {

    private data class ChatEvent(
        val filterAction: ChatTextFilterAction,
        val text: String,
        val msg: PublicChatMessage,
        val icon: Int = 0
    )

    private val huffman: HuffmanCodec = get()

    override fun handle(
        player: Player,
        message: PublicChatMessage
    ) {

        val decompressedString = ByteArray(256)

        huffman.decompress(message.data, decompressedString, message.messageLength)

        val unpackedString = String(decompressedString, 0, message.messageLength)

        //todo: CHANGE RIGHTS


        Observable.just(unpackedString)
            .zipWith(
                RSPluginManager.getExtensions<ChatTextFilterFactory>()
                    .toObservable()
                    .defaultIfEmpty(DefaultChatFilterFactory())
            )
            .map {
                ChatEvent(
                    it.second.registerChatTextFilter(
                        message.chatType,
                        message.effect,
                        message.color
                    ),
                    it.first,
                    message
                )
            }
            .filter { it.filterAction.checkChatText(it.text) }
            .subscribe {
                player.pendingPublicChatMessage =
                    PublicChatText(it.text, it.icon, it.msg.chatType, it.msg.effect, it.msg.color)
                player.syncInfo.addMaskFlag(PlayerUpdateFlag.PUBLIC_CHAT)
            }.dispose()
    }

}