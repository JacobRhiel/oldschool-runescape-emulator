package rs.emulator.network.packet.listener

import io.reactivex.rxkotlin.toObservable
import rs.emulator.entity.actor.player.messages.IWidgetMessages
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.LocActionMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.entity.actions.ObjectActionFactory
import rs.emulator.region.WorldCoordinate
import rs.emulator.world.GameWorld

/**
 *
 * @author javatar
 */

class LocActionListener : GamePacketListener<LocActionMessage>
{

    override fun handle(
        player: Player,
        message: LocActionMessage
    )
    {

        //TODO - validate if object exists on x and y

        player.world.mapGrid.fetchRegion(player.coordinate.x, player.coordinate.y)

        player.targetCoordinate = WorldCoordinate(message.x, message.y, player.coordinate.plane)

        val path = player.find(player.targetCoordinate!!)

        val start = player.coordinate

        println("Path result for loc: ${message.locId}, $path")

        RSPluginManager.getExtensions<ObjectActionFactory>()
            .toObservable()
            .map {
                it.registerObjectActions(
                    message.locId,
                    message.x,
                    message.y,
                    message.controlPressed
                )
            }.subscribe({
                val obj = GameWorld.objects.find { obj -> obj.id == message.locId }
                if (obj != null) {
                    it.handleObjectAction(player, obj, message.option)
                }
            }, {
                player.messagesFromType<IWidgetMessages>()
                    .sendChatMessage("Nothing interesting happens.")
            })

    }
}