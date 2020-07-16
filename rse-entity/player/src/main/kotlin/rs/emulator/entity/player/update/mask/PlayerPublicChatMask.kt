package rs.emulator.entity.player.update.mask

import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.cache.store.VirtualFileStore
import rs.emulator.encryption.huffman.HuffmanCodec
import rs.emulator.entity.player.Player
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.entity.update.flag.UpdateFlag
import rs.emulator.entity.update.mask.UpdateMask
import rs.emulator.network.packet.GamePacketBuilder

/**
 *
 * @author Chk
 */
class PlayerPublicChatMask : UpdateMask<Player>, KoinComponent
{

    private val huffman: HuffmanCodec = get()

    override fun shouldGenerate(entity: Player): Boolean = (entity.pendingPublicChatMessage != null && entity.syncInfo.hasMaskFlag(PlayerUpdateFlag.PUBLIC_CHAT))

    override fun generate(entity: Player, builder: GamePacketBuilder)
    {

        val message = entity.pendingPublicChatMessage!!

        val auto = false //todo

        val compressedString = ByteArray(256)

        val length = huffman.compress(message.text, compressedString)

        builder.put(DataType.SHORT, DataOrder.LITTLE, (message.color shl 8) or message.effect)

        builder.put(DataType.BYTE, DataTransformation.NEGATE, message.icon)

        builder.put(DataType.BYTE, if(auto) 1 else 0)

        builder.put(DataType.BYTE, DataTransformation.ADD, length + 1)

        builder.putSmart(message.text.length)

        builder.putBytes(compressedString, 0, length)

        entity.syncInfo.removeMaskFlag(PlayerUpdateFlag.PUBLIC_CHAT)

    }

    override fun fetchFlag(): UpdateFlag = PlayerUpdateFlag.PUBLIC_CHAT

}