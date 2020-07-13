package rs.emulator.world.task

import rs.emulator.network.packet.atest.UpdatePlayerSyncMessage
import rs.emulator.service.event.IEvent
import rs.emulator.world.repository.WorldRepository

/**
 *
 * @author Chk
 */
object UpdatePlayerSynchronizationEvent : IEvent {
    override fun execute() {
        WorldRepository.players.forEach {
            it.outgoingPackets.offer(UpdatePlayerSyncMessage(it))
        }
    }
}