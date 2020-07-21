package rs.emulator.entity.player.update.mask

import rs.emulator.buffer.manipulation.DataOrder
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

        return entity.pendingGraphic != -1 && entity.syncInfo.hasMaskFlag(PlayerUpdateFlag.GRAPHIC)

    }

    override fun generate(entity: Player, builder: GamePacketBuilder)
    {

        builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, entity.pendingGraphic)

        builder.put(DataType.INT, DataOrder.MIDDLE,(entity.pendingGraphicHeight shl 16) or entity.pendingGraphicDelay)

    }

    override fun fetchFlag(): UpdateFlag = PlayerUpdateFlag.GRAPHIC

}