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
class PlayerAnimationMask : UpdateMask<Player>
{

    override fun shouldGenerate(entity: Player): Boolean
    {

        return entity.syncInfo.hasMaskFlag(PlayerUpdateFlag.ANIMATION)

    }

    override fun generate(entity: Player, builder: GamePacketBuilder)
    {

        builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, 0)//animation id

        builder.put(DataType.BYTE, DataTransformation.SUBTRACT, 0)//animation delay until begin

    }

    override fun fetchFlag(): UpdateFlag = PlayerUpdateFlag.ANIMATION

}