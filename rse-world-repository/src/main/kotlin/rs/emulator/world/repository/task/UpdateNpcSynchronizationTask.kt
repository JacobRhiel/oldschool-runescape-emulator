package rs.emulator.world.repository.task

import kotlinx.coroutines.channels.sendBlocking
import rs.emulator.network.packet.message.outgoing.UpdateNpcSyncMessage
import rs.emulator.service.event.IEvent
import rs.emulator.world.repository.WorldRepository

/**
 *
 * @author Chk
 */
object UpdateNpcSynchronizationTask : IEvent
{

    override fun execute()
    {

        WorldRepository.players.forEach {
            it.outgoingPackets.sendBlocking(
                UpdateNpcSyncMessage(
                    it
                )
            )
        }

    }
}