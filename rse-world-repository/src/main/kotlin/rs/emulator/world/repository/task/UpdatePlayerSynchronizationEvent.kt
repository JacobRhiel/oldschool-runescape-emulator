package rs.emulator.world.repository.task

import rs.emulator.network.packet.message.outgoing.UpdatePlayerSyncMessage
import rs.emulator.service.event.IEvent
import rs.emulator.world.repository.WorldRepository

/**
 *
 * @author Chk
 */
object UpdatePlayerSynchronizationEvent : IEvent
{

    override fun execute()
    {
        WorldRepository.players.forEach {
            it.outgoingPackets.offer(
                UpdatePlayerSyncMessage(
                    it
                )
            )

        }

    }
}