package rs.emulator.entity.player.update.mask

import rs.dusk.engine.model.entity.Direction
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
class PlayerMovementMask : UpdateMask<Player>
{

    override fun generate(entity: Player, builder: GamePacketBuilder)
    {

        builder.put(DataType.BYTE, DataTransformation.NEGATE,
            when {
                entity.movement.runStep != Direction.NONE -> 2
                entity.movement.walkStep != Direction.NONE -> 1
                entity.pendingTeleport != null -> 127
                else -> 0
            }
        )//value is 127 if teleport, 2 if run, 1 if walk else? 0

        entity.syncInfo.removeMaskFlag(PlayerUpdateFlag.MOVEMENT)

    }

    override fun fetchFlag(): UpdateFlag = PlayerUpdateFlag.MOVEMENT

    override fun shouldGenerate(entity: Player): Boolean = entity.syncInfo.hasMaskFlag(PlayerUpdateFlag.MOVEMENT)

}