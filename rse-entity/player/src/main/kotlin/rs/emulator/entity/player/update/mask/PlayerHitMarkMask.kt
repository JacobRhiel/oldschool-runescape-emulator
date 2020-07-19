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
class PlayerHitMarkMask : UpdateMask<Player>
{

    override fun shouldGenerate(entity: Player): Boolean
    {

        return entity.syncInfo.hasMaskFlag(PlayerUpdateFlag.HIT_MARK)

    }

    override fun generate(entity: Player, builder: GamePacketBuilder)
    {

        val hitMaskSize = 0

        builder.put(DataType.BYTE, DataTransformation.ADD, hitMaskSize)

        if(hitMaskSize == 0)
            builder.put(DataType.SMART, 32766)
        else
            builder.put(DataType.SMART, 32767)

        //todo: smart -type
        //todo: smart - damage

        //todo: smart- delay



    }

    override fun fetchFlag(): UpdateFlag = PlayerUpdateFlag.HIT_MARK

}