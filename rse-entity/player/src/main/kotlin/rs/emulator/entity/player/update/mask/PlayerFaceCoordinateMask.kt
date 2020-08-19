package rs.emulator.entity.player.update.mask

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.entity.player.Player
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.entity.update.flag.UpdateFlag
import rs.emulator.entity.update.mask.UpdateMask
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.region.as30BitInteger

/**
 *
 * @author Chk
 */
class PlayerFaceCoordinateMask : UpdateMask<Player>
{

    override fun shouldGenerate(entity: Player): Boolean
    {

        return entity.syncInfo.hasMaskFlag(PlayerUpdateFlag.FACE_COORDINATE)

    }

    override fun generate(entity: Player, builder: GamePacketBuilder)
    {

        //todo: add facing coordinate.
        builder.put(DataType.SHORT, entity.coordinate.as30BitInteger)

    }

    override fun fetchFlag(): UpdateFlag = PlayerUpdateFlag.FACE_COORDINATE
}