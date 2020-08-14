package rs.emulator.world.repository.task

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.sendBlocking
import rs.emulator.network.packet.message.outgoing.UpdatePlayerSyncMessage
import rs.emulator.service.event.IEvent
import rs.emulator.world.repository.WorldRepository

/**
 *
 * @author Chk
 */
@ExperimentalCoroutinesApi
object UpdatePlayerSynchronizationEvent : IEvent
{

    override fun execute()
    {
        WorldRepository.players.forEach {
            it.outgoingPackets.sendBlocking(
                UpdatePlayerSyncMessage(
                    it
                )
            )
        }
    }
}