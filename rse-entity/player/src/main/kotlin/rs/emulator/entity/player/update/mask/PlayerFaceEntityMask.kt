package rs.emulator.entity.player.update.mask

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
class PlayerFaceEntityMask : UpdateMask<Player>
{

    override fun shouldGenerate(entity: Player): Boolean
    {

        return entity.syncInfo.hasMaskFlag(PlayerUpdateFlag.FACE_ENTITY)

    }

    override fun generate(entity: Player, builder: GamePacketBuilder)
    {

        builder.put(DataType.SHORT, 0)//todo: player target index

    }

    override fun fetchFlag(): UpdateFlag = PlayerUpdateFlag.FACE_ENTITY

}