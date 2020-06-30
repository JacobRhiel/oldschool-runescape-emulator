package rs.emulator.network.packet.atest

import io.netty.buffer.Unpooled
import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.region.XteaKeyService

/**
 *
 * @author Chk
 */
class RebuildRegionEncoder : PacketEncoder<RebuildRegionMessage>()
{

    /**
     * The size of a chunk, in tiles.
     */
    val CHUNK_SIZE = 8

    /**
     * The amount of chunks in a region.
     */
    val CHUNKS_PER_REGION = 13

    /**
     * The size of the viewport a [gg.rsmod.game.model.entity.Player] can
     * 'see' at a time, in tiles.
     */
    val MAX_VIEWPORT = CHUNK_SIZE * CHUNKS_PER_REGION

    override fun encode(message: RebuildRegionMessage, player: Player, builder: GamePacketBuilder)
    {

        if(message.login)
        {

            builder.switchToBitAccess()

            val tileHash = (message.z and 0x3FFF) or ((message.x and 0x3FFF) shl 14) or ((message.height and 0x3) shl 28)

            builder.putBits(30, tileHash)//message.tile.as30BitInteger)

            for (i in 1 until 2048)
                if (i != message.index)
                    builder.putBits(18, 0/*message.playerTiles[i]*/)

            builder.switchToByteAccess()

        }

        val chunkX = message.x shr 3

        val chunkZ = message.z shr 3

        builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, chunkZ)

        builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, chunkX)

        val lx = (chunkX - (MAX_VIEWPORT shr 4)) shr 3
        val rx = (chunkX + (MAX_VIEWPORT shr 4)) shr 3
        val lz = (chunkZ - (MAX_VIEWPORT shr 4)) shr 3
        val rz = (chunkZ + (MAX_VIEWPORT shr 4)) shr 3

        val buf = Unpooled.buffer(Short.SIZE_BYTES + (Int.SIZE_BYTES * 10))

        var forceSend = false

        if ((chunkX / 8 == 48 || chunkX / 8 == 49) && chunkZ / 8 == 48)
            forceSend = true

        if (chunkX / 8 == 48 && chunkZ / 8 == 148)
            forceSend = true

        var count = 0

        buf.writeShort(count) // Client always read as unsigned short

        for (x in lx..rx)
        {
            for (z in lz..rz)
            {
                if (!forceSend || z != 49 && z != 149 && z != 147 && x != 50 && (x != 49 || z != 47)) {
                    val region = z + (x shl 8)
                    val keys = XteaKeyService.get(region)
                    for (xteaKey in keys)
                        buf.writeInt(xteaKey) // Client always reads as int
                    count++
                }
            }
        }
        buf.setShort(0, count)

        builder.putBytes(buf)

    }

}