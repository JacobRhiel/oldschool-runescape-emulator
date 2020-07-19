package rs.emulator.entity.player.update.mask

import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.entity.player.Player
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.entity.update.flag.UpdateFlag
import rs.emulator.entity.update.mask.UpdateMask
import rs.emulator.network.packet.GamePacketBuilder

/**
 *
 * @author Chk
 */
class PlayerGraphicMask : UpdateMask<Player>
{
    override fun shouldGenerate(entity: Player): Boolean
    {

        return entity.syncInfo.hasMaskFlag(PlayerUpdateFlag.GRAPHIC)

    }

    override fun generate(entity: Player, builder: GamePacketBuilder)
    {

        builder.put(DataType.BYTE, DataTransformation.NEGATE, 0)//graphic id

    }

    override fun fetchFlag(): UpdateFlag = PlayerUpdateFlag.GRAPHIC

}