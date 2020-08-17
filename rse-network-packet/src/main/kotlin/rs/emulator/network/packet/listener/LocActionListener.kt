package rs.emulator.network.packet.listener

import io.reactivex.rxkotlin.toObservable
import rs.emulator.entity.actor.npc.Npc
import rs.emulator.entity.actor.player.messages.IWidgetMessages
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.LocActionMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.entity.actions.ObjectActionFactory
import rs.emulator.region.WorldCoordinate
import rs.emulator.region.coordinate.Coordinate
import rs.emulator.world.GameWorld

/**
 *
 * @author javatar
 */

class LocActionListener : GamePacketListener<LocActionMessage>
{

    /**
     * Sets up walk and run changes based on [Steps] queue.
     */
    fun step(player: Player) {
        val movement = player.movement
        val steps = movement.steps
        if (steps.peek() != null) {
            var step = steps.poll()
            if (!movement.traversal.blocked(player.coordinate, step)) {
                movement.walkStep = step
                movement.delta = step.delta
                //player.movementType = PlayerMoveType.Walk
                //player.temporaryMoveType = PlayerMoveType.Walk
                if (movement.running) {
                    if (steps.peek() != null) {
                        val tile = player.coordinate.add(step.delta)
                        step = steps.poll()
                        if (!movement.traversal.blocked(tile, step)) {
                            movement.runStep = step
                            movement.delta = movement.delta.add(step.delta)
                            // player.movementType = PlayerMoveType.Run
                            // player.temporaryMoveType = PlayerMoveType.Run
                        }
                    } else {
                        // player.movementType = PlayerMoveType.Walk
                        //player.temporaryMoveType = PlayerMoveType.Run
                    }
                }
            }
        }
    }

    /**
     * Moves the player tile and emits Moved event
     */
    fun move(player: Player, npc: Npc)
    {

        val movement = player.movement

        movement.lastCoordinate = npc.coordinate

        //if(player.coordinate == player.targetCoordinate)
        //    movement.clear()
        if (movement.delta != Coordinate.EMPTY)
        {
            val from = npc.coordinate
            val to = npc.coordinate.add(movement.delta)
            npc.lastCoordinate.set(npc.coordinate)
            npc.coordinate.set(to.x, to.y, to.plane)
            //player.syncInfo.addMaskFlag(PlayerUpdateFlag.MOVEMENT)
            //bus.emit(Moved(player, from, player.tile))
        }
    }

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

        player.movement.steps.iterator().forEach {
            val index = player.viewport.nextNpcIndex
            player.viewport.unsyncedNpcs[index] = Npc(index, 1, coordinate = start.add(player.movement.delta) as WorldCoordinate)
        }

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