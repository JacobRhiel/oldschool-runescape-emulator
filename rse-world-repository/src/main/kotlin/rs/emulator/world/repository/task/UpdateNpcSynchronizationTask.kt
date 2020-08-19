package rs.emulator.world.repository.task

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.sendBlocking
import rs.emulator.network.packet.message.outgoing.UpdateNpcSyncMessage
import rs.emulator.service.event.IEvent
import rs.emulator.world.GameWorld
import rs.emulator.world.repository.WorldRepository

/**
 *
 * @author Chk
 */
@ExperimentalCoroutinesApi
object UpdateNpcSynchronizationTask : IEvent
{

    override fun execute()
    {

        WorldRepository.players.forEach { player ->

            val viewport = player.viewport

            GameWorld.npcs.filter { !viewport.localNpcs.containsKey(it.index) }.forEach { viewport.unsuncNpcs[it.index] = it }

            if(viewport.unsuncNpcs.isNotEmpty() || viewport.localNpcCount > 0)
                player.outgoingPackets.sendBlocking(UpdateNpcSyncMessage(player))

        }

    }
}