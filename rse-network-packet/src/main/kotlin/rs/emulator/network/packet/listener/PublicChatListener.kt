package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.cache.store.VirtualFileStore
import rs.emulator.encryption.huffman.HuffmanCodec
import rs.emulator.entity.player.Player
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.network.packet.message.incoming.PublicChatMessage

/**
 *
 * @author Chk
 */
class PublicChatListener : GamePacketListener<PublicChatMessage>, KoinComponent
{

    private val fileStore: VirtualFileStore = get()

    private val huffman: HuffmanCodec = fileStore.huffman

    override fun handle(channel: Channel, player: Player, message: PublicChatMessage)
    {

        val decompressedString = ByteArray(256)

        println("message length: " + message.messageLength)

        huffman.decompress(message.data, decompressedString, message.messageLength)

        val unpackedString = String(decompressedString, 0, message.length)

        println("unpacked string...: $unpackedString")

        //todo: CHANGE RIGHTS
        player.pendingPublicChatMessage = rs.emulator.entity.player.chat.PublicChatMessage(unpackedString, 0, message.chatType, message.effect, message.color)

        player.syncInfo.addMaskFlag(PlayerUpdateFlag.PUBLIC_CHAT)


    }

}