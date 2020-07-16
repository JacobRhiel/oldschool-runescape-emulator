package rs.emulator.world.repository.task

import rs.emulator.network.packet.message.outgoing.RebuildRegionMessage
import rs.emulator.service.event.IEvent
import rs.emulator.world.repository.WorldRepository

/**
 *
 * @author Chk
 */
object PreUpdatePlayerSynchronizationTask: IEvent
{

    override fun execute()
    {

        WorldRepository.players.forEach {

            it.outgoingPackets.offer(RebuildRegionMessage(false, 1/*it.index*/, it.coordinate.x, it.coordinate.z, it.coordinate.plane, it.coordinate.as30BitInteger))

        }

    }

}