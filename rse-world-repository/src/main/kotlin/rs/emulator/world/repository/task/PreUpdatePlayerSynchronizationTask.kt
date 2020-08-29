package rs.emulator.world.repository.task

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.outgoing.RebuildRegionMessage
import rs.emulator.region.WorldCoordinate
import rs.emulator.region.as30BitInteger
import rs.emulator.region.coordinate.Coordinate
import rs.emulator.region.events.MoveCoordinateEvent
import rs.emulator.regions.zones.RegionZone
import rs.emulator.service.event.IEvent
import rs.emulator.utilities.contexts.scopes.ActorScope
import rs.emulator.utilities.koin.get
import rs.emulator.utilities.logger.debug
import rs.emulator.world.regions.RegionZoneManager
import rs.emulator.world.repository.WorldRepository

/**
 *
 * @author Chk
 */
@ExperimentalCoroutinesApi
object PreUpdatePlayerSynchronizationTask : IEvent {

    @ExperimentalCoroutinesApi
    override fun execute() {

        WorldRepository.players.forEach { player ->

            val locked = player.movement.frozen/* || !player.viewport.loaded*/

            if (!locked) {
                step(player)
                move(player)
                RegionZoneManager.updateActorZone(player)
            }

            val lastRegionBase = player.lastRegion

            val currentCoordinate = player.coordinate

            if (lastRegionBase != null && shouldRebuildRegion(lastRegionBase, currentCoordinate)) {

                player.outgoingPackets.sendBlocking(
                    RebuildRegionMessage(
                        false,
                        player.index,
                        player.coordinate.x,
                        player.coordinate.y,
                        player.coordinate.plane,
                        player.coordinate.as30BitInteger
                    )
                )

            }

            player.actions.cycle()

            player.incomingPackets.consumeAsFlow()
                .onEach { println("Incoming Packet ${it.msg.opcode}") }
                .onEach { it.handler.handle(player, it.msg) }
                .launchIn(get<ActorScope>())


        }

    }

    private fun shouldRebuildRegion(old: Coordinate, new: Coordinate): Boolean {

        val dx = new.x - old.x

        val dz = new.y - old.y

        return dx <= 15 || dx >= 104 - 15 - 1 || dz <= 15 || dz >= 104 - 15 - 1

    }

    /**
     * Sets up walk and run changes based on [Steps] queue.
     */
    fun step(player: Player) {
        val movement = player.movement
        val steps = movement.steps
        //println("step count: " + steps.size)
        if (steps.peek() != null) {
            val step = steps.poll()
            if (!movement.traversal.blocked(player.coordinate, step)) {
                movement.walkStep = step
                movement.delta = step.delta
                //player.movementType = PlayerMoveType.Walk
                //player.temporaryMoveType = PlayerMoveType.Walk
                if (movement.running) {
                    if (steps.peek() != null) {
                        val tile = player.coordinate.add(step.delta)
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
            } else {
                println("path blocked for tile: " + player.coordinate + ", step: $step")
            }
        }
    }

    /**
     * Moves the player tile and emits Moved event
     */
    fun move(player: Player) {
        val movement = player.movement
        if (player.coordinate == player.targetCoordinate) {
            player.targetCoordinate = null
            movement.clear()
        } else if (movement.delta != Coordinate.EMPTY) {
            val from = player.coordinate
            val to = player.coordinate.add(movement.delta)
            player.lastCoordinate.set(player.coordinate)
            player.coordinateState[to.x, to.y, to.plane] = MoveCoordinateEvent(player, from, to as WorldCoordinate)
        }
    }

}