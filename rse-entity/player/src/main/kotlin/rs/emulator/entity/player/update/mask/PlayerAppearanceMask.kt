package rs.emulator.entity.player.update.mask

import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.writer.BufferedWriter
import rs.emulator.entity.player.Player
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.entity.update.flag.UpdateFlag
import rs.emulator.entity.update.mask.UpdateMask
import rs.emulator.network.packet.GamePacketBuilder

/**
 *
 * @author Chk
 */
class PlayerAppearanceMask : UpdateMask<Player>
{

    override fun generate(entity: Player, builder: GamePacketBuilder)
    {

        val writer = BufferedWriter()

        writer.put(DataType.BYTE, 0)//gender
        writer.put(DataType.BYTE, -1)//skull icon
        writer.put(DataType.BYTE, -1)//prayer icon

        var styles = intArrayOf(0, 0, 0, 0, 21, 0, 26, 38, 3, 33, 42, 14)

        for(index in 0 until 12)
        {

                if(styles[index] == 0)
                    writer.put(DataType.BYTE, 0)
                else
                {
                    writer.put(DataType.BYTE, 1)
                    writer.put(DataType.BYTE, styles[index])
                }

        }

        for (i in 0 until 5)
        {
            val colors = arrayOf(3, 4, 2, 3, 2)
            writer.put(DataType.BYTE, colors[i])
        }

        val animations = intArrayOf(808, 823, 823, 820, 821, 822, 824)

        animations.forEach { anim ->
            writer.put(DataType.SHORT, anim)
        }

        writer.putString("Gpi")
        writer.put(DataType.BYTE, /*other.combatLevel*/3)
        writer.put(DataType.SHORT,0)//skill level
        writer.put(DataType.BYTE, 0)//is hidden

        builder.put(DataType.BYTE, DataTransformation.SUBTRACT, writer.byteBuf.readableBytes())

        builder.putBytes(DataTransformation.ADD, writer.byteBuf)

        entity.syncInfo.removeMaskFlag(PlayerUpdateFlag.APPEARANCE)

    }

    override fun fetchFlag(): UpdateFlag = PlayerUpdateFlag.APPEARANCE

    override fun shouldGenerate(entity: Player): Boolean = entity.syncInfo.hasMaskFlag(PlayerUpdateFlag.APPEARANCE)

}