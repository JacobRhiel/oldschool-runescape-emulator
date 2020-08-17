package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.entity.actor.npc.Npc
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.UpdateNpcSyncMessage

/**
 *
 * @author Chk
 */
class UpdateNpcSyncEncoder : PacketEncoder<UpdateNpcSyncMessage<Player>>()
{

    override fun encode(message: UpdateNpcSyncMessage<Player>, builder: GamePacketBuilder)
    {

        val player = message.player

        val maskBuilder = GamePacketBuilder()

        val viewport = player.viewport

        builder.switchToBitAccess()

        builder.putBits(8, viewport.localNpcCount)

        viewport.localNpcs.iterator().forEachRemaining {

            val npc = (it.value as Npc)

            builder.putBits(1, 1)//0 if skip

            builder.putBits(2, 0)//0 = no movement

            maskBuilder.put(DataType.BYTE, 0 and 0xFF)

        }

        viewport.unsyncedNpcs.values.iterator().forEachRemaining {

            if(player.viewport.localNpcs.containsValue(it))
                return@forEachRemaining

            val npc = it as Npc

            var dx = npc.coordinate.x - player.coordinate.x

            var dz = npc.coordinate.y - player.coordinate.y

            if (dx < 15)
                dx += 32

            if (dz < 15)
                dz += 32

            println("index: ${it.index}")

            builder.putBits(15, it.index)

            builder.putBits(3, 0)//dir

            builder.putBits(1, 1)//0 no update

            builder.putBits(1, 1)//0 no update

            builder.putBits(14, npc.id)

            builder.putBits(5, dz)

            builder.putBits(5, dx)

            maskBuilder.put(DataType.BYTE, 0 and 0xFF)

            player.viewport.localNpcs[npc.index] = npc

            player.viewport.unsyncedNpcs.remove(npc.index)

        }

        builder.switchToByteAccess()

        builder.putBytes(maskBuilder.buffer)

    }

}