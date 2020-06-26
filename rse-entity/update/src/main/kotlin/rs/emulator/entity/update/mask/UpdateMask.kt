package rs.emulator.entity.update.mask

import rs.emulator.entity.Entity
import rs.emulator.entity.update.flag.UpdateFlag
import rs.emulator.network.packet.GamePacketBuilder

/**
 *
 * @author Chk
 */
interface UpdateMask<T : Entity>
{

    fun generate(entity: T, builder: GamePacketBuilder)

    fun fetchFlag(): UpdateFlag

}