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
class PlayerContextMenuMask : UpdateMask<Player>
{

    override fun shouldGenerate(entity: Player): Boolean
    {

        return entity.syncInfo.hasMaskFlag(PlayerUpdateFlag.CONTEXT_MENU)

    }

    override fun generate(entity: Player, builder: GamePacketBuilder)
    {

        for(actionIndex in 0 until 3)
        {

            //todo: parse player actions on target
            builder.putString("This is an action")

        }

    }

    override fun fetchFlag(): UpdateFlag = PlayerUpdateFlag.CONTEXT_MENU

}