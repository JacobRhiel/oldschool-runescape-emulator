package rs.emulator.world.repository.task

import rs.emulator.entity.player.Player
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.region.coordinate.Coordinate
import rs.emulator.service.event.IEvent
import rs.emulator.world.repository.WorldRepository

/**
 *
 * @author Chk
 */
object PlayerWalkingTask: IEvent
{

    override fun execute()
    {

        WorldRepository.players.forEach { player ->

            val locked = player.movement.frozen/* || !player.viewport.loaded*/

            if(!locked) {
                step(player)
                move(player)
            }

        }

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
    fun move(player: Player) {
        val movement = player.movement
        movement.lastCoordinate = player.coordinate
        if (movement.delta != Coordinate.EMPTY) {
            val from = player.coordinate
            val to = player.coordinate.add(movement.delta)
            println("from: $from, to: $to")
            player.coordinate.set(to.x, to.z, to.plane)
            //player.syncInfo.addMaskFlag(PlayerUpdateFlag.MOVEMENT)
            //bus.emit(Moved(player, from, player.tile))
        }
    }

}