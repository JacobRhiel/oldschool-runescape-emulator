package rs.emulator.entity.player.update.mask

import rs.emulator.entity.player.Player
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.entity.update.flag.UpdateFlag
import rs.emulator.entity.update.mask.UpdateMask
import rs.emulator.network.packet.GamePacketBuilder

/**
 *
 * @author Chk
 */
class PlayerForceTextMask : UpdateMask<Player>
{

    override fun shouldGenerate(entity: Player): Boolean
    {

        return entity.syncInfo.hasMaskFlag(PlayerUpdateFlag.FORCE_TEXT)

    }

    override fun generate(entity: Player, builder: GamePacketBuilder)
    {

        //todo: add player method.
        builder.putString("This is a test!")

    }

    override fun fetchFlag(): UpdateFlag = PlayerUpdateFlag.FORCE_TEXT

}