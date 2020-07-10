package rs.emulator.entity.update.task

import rs.emulator.entity.Entity
import rs.emulator.entity.update.mask.UpdateMask
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.packet.api.GamePacket
import rs.emulator.service.event.IEvent

/**
 *
 * @author Chk
 */
interface UpdateSynchronizationTask<T : Entity> : IEvent
{

    fun execute(entity: T)

    fun createGamePacket(builder: GamePacketBuilder) : GamePacket

    fun generateMaskBuffer(entity: T, masks: List<UpdateMask<T>>, builder: GamePacketBuilder)

    fun fetchMasks() : List<UpdateMask<T>>

}
