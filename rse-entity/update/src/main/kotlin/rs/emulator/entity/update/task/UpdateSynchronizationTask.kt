package rs.emulator.entity.update.task

import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.engine.service.event.Event
import rs.emulator.entity.Entity
import rs.emulator.entity.update.mask.UpdateMask
import rs.emulator.network.packet.GamePacket
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
interface UpdateSynchronizationTask<T : Entity> : Event
{

    fun execute(entity: T)

    fun createGamePacket(builder: GamePacketBuilder) : GamePacket

    fun generateMaskBuffer(entity: T, masks: List<UpdateMask<T>>, builder: GamePacketBuilder)

    fun fetchMasks() : List<UpdateMask<T>>

}
