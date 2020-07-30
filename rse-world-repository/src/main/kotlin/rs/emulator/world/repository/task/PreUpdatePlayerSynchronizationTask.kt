package rs.emulator.world.repository.task

import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.outgoing.RebuildRegionMessage
import rs.emulator.region.WorldCoordinate
import rs.emulator.region.coordinate.Coordinate
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

            val locked = it.movement.frozen/* || !player.viewport.loaded*/

            if(!locked) {
                step(it)
                move(it)
            }

            val lastRegionBase = it.lastRegion

            val currentCoordinate = it.coordinate

            if(lastRegionBase != null && shouldRebuildRegion(lastRegionBase, currentCoordinate))
            {

                it.outgoingPackets.offer(RebuildRegionMessage(false, 1/*it.index*/, it.coordinate.x, it.coordinate.z, it.coordinate.plane, it.coordinate.as30BitInteger))

            }

            it.actions.cycle()

        }

    }

    private fun shouldRebuildRegion(old: Coordinate, new: Coordinate): Boolean
    {

        val dx = new.x - old.x

        val dz = new.z - old.z

        return dx <= 15 || dx >= 104 - 15 - 1 || dz <= 15 || dz >= 104 - 15 - 1

    }

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
    fun move(player: Player)
    {

        val movement = player.movement

        movement.lastCoordinate = player.coordinate

        if(player.coordinate == player.targetCoordinate)
            movement.clear()
        else if (movement.delta != Coordinate.EMPTY)
        {
            val from = player.coordinate
            val to = player.coordinate.add(movement.delta)
            println("from: $from, to: $to")
            player.lastCoordinate.set(player.coordinate)
            player.coordinate.set(to.x, to.z, to.plane)
            //player.syncInfo.addMaskFlag(PlayerUpdateFlag.MOVEMENT)
            //bus.emit(Moved(player, from, player.tile))
        }
    }

}