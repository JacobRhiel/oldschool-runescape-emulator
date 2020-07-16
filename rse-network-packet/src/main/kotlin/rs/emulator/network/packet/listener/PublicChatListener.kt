package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import io.reactivex.rxkotlin.toObservable
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.cache.store.VirtualFileStore
import rs.emulator.encryption.huffman.HuffmanCodec
import rs.emulator.entity.player.Player
import rs.emulator.entity.player.chat.PublicChatText
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.network.packet.message.incoming.PublicChatMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.ChatTextFilterFactory

/**
 *
 * @author Chk
 */
class PublicChatListener : GamePacketListener<PublicChatMessage>, KoinComponent {

    private val fileStore: VirtualFileStore = get()

    private val huffman: HuffmanCodec = fileStore.huffman

    override fun handle(channel: Channel, player: Player, message: PublicChatMessage) {

        val decompressedString = ByteArray(256)

        huffman.decompress(message.data, decompressedString, message.messageLength)

        val unpackedString = String(decompressedString, 0, message.length)

        //todo: CHANGE RIGHTS

        RSPluginManager.getExtensions<ChatTextFilterFactory>()
            .toObservable()
            .map {
                it.registerChatTextFilter(
                    message.chatType,
                    message.effect,
                    message.color
                )
            }
            .filter { it.checkChatText(unpackedString) }
            .subscribe({
                player.pendingPublicChatMessage =
                    PublicChatText(unpackedString, 0, message.chatType, message.effect, message.color)
                player.syncInfo.addMaskFlag(PlayerUpdateFlag.PUBLIC_CHAT)
            }, {
                it.printStackTrace()
            }).dispose()
    }

}