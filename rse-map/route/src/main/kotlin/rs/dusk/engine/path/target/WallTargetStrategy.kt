package rs.dusk.engine.path.target

import rs.dusk.engine.model.entity.Direction
import rs.dusk.engine.model.entity.Size
import rs.dusk.engine.model.world.map.collision.CollisionFlag
import rs.dusk.engine.model.world.map.collision.Collisions
import rs.dusk.engine.model.world.map.collision.check
import rs.dusk.engine.model.world.map.collision.flag
import rs.dusk.engine.path.TargetStrategy
import rs.emulator.region.coordinate.Coordinate

/**
 * Checks if within interact range of a wall
 * e.g On the correct side to view a painting on a wall
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since May 18, 2020
 */
data class WallTargetStrategy(
    private val collisions: Collisions,
    override val tile: Coordinate,
    override val size: Size = Size.TILE,
    val rotation: Int,
    val type: Int
) : TargetStrategy {

    override fun reached(currentX: Int, currentY: Int, plane: Int, size: Size): Boolean {
        val sizeXY = size.width
        // Check if under
        if (sizeXY == 1 && currentX == tile.x && currentY == tile.z) {
            return true
        } else if (sizeXY != 1 && tile.x >= currentX && tile.x <= currentX + sizeXY - 1 && tile.z <= tile.z + sizeXY - 1) {
            return true
        }

        if (sizeXY == 1) {
            if (type == 0) {
                var direction = Direction.cardinal[rotation + 3 and 0x3]
                if (currentX == tile.x + direction.delta.x && currentY == tile.z + direction.delta.z) {
                    return true
                }
                direction = Direction.cardinal[rotation and 0x3]
                if (currentX == tile.x - direction.delta.x && currentY == tile.z - direction.delta.z && !collisions.check(
                        currentX,
                        currentY,
                        plane,
                        direction.wall()
                    )
                ) {
                    return true
                }
                val inverse = direction.inverse()
                if (currentX == tile.x - inverse.delta.x && currentY == tile.z - inverse.delta.z && !collisions.check(
                        currentX,
                        currentY,
                        plane,
                        inverse.wall()
                    )
                ) {
                    return true
                }
            }
            if (type == 2) {
                val direction = Direction.ordinal[rotation and 0x3]
                val horizontal = direction.horizontal()
                if (currentX == tile.x + horizontal.delta.x && currentY == tile.z) {
                    return true
                }
                val vertical = direction.vertical()
                if (currentX == tile.x && currentY == tile.z + vertical.delta.z) {
                    return true
                }
                if (currentX == tile.x - horizontal.delta.x && currentY == tile.z && !collisions.check(
                        currentX,
                        currentY,
                        plane,
                        horizontal.wall()
                    )
                ) {
                    return true
                }
                if (currentX == tile.x && currentY == tile.z - vertical.delta.z && !collisions.check(
                        currentX,
                        currentY,
                        plane,
                        vertical.wall()
                    )
                ) {
                    return true
                }
            }
            if (type == 9) {
                Direction.ordinal.forEach { direction ->
                    if (currentX == tile.x - direction.delta.x && currentY == tile.z - direction.delta.z && !collisions.check(
                            currentX,
                            currentY,
                            plane,
                            direction.flag()
                        )
                    ) {
                        return true
                    }
                }
                return false
            }
        } else {
            val sizeX = sizeXY + currentX - 1
            val sizeY = sizeXY + currentY - 1
            if (type == 0) {
                if (rotation == 0) {
                    if (currentX == tile.x - sizeXY && tile.z >= currentY && tile.z <= sizeY) {
                        return true
                    }
                    if (currentY == tile.z + 1 && tile.x in currentX..sizeX && !collisions.check(
                            tile.x,
                            currentY,
                            plane,
                            Direction.SOUTH.wall()
                        )
                    ) {
                        return true
                    }
                    if (currentY == tile.z - sizeXY && tile.x in currentX..sizeX && !collisions.check(
                            tile.x,
                            sizeY,
                            plane,
                            Direction.NORTH.wall()
                        )
                    ) {
                        return true
                    }
                } else if (rotation == 1) {
                    if (currentY == tile.z + 1 && tile.x >= currentX && tile.x <= sizeX) {
                        return true
                    }
                    if (currentX == tile.x - sizeXY && tile.z >= currentY && tile.z <= sizeY && !collisions.check(
                            sizeX,
                            tile.z,
                            plane,
                            Direction.EAST.wall()
                        )
                    ) {
                        return true
                    }
                    if (currentX == tile.x + 1 && tile.z >= currentY && tile.z <= sizeY && !collisions.check(
                            currentX,
                            tile.z,
                            plane,
                            Direction.WEST.wall()
                        )
                    ) {
                        return true
                    }
                } else if (rotation == 2) {
                    if (currentX == tile.x + 1 && tile.z >= currentY && tile.z <= sizeY) {
                        return true
                    }
                    if (currentY == tile.z + 1 && tile.x in currentX..sizeX && !collisions.check(
                            tile.x,
                            currentY,
                            plane,
                            Direction.SOUTH.wall()
                        )
                    ) {
                        return true
                    }
                    if (currentY == tile.z - sizeXY && tile.x in currentX..sizeX && !collisions.check(
                            tile.x,
                            sizeY,
                            plane,
                            Direction.NORTH.wall()
                        )
                    ) {
                        return true
                    }
                } else if (rotation == 3) {
                    if (currentY == tile.z - sizeXY && currentX <= tile.x && sizeX >= tile.x) {
                        return true
                    }
                    if (currentX == tile.x - sizeXY && tile.z >= currentY && sizeY >= tile.z && !collisions.check(
                            sizeX,
                            tile.z,
                            plane,
                            Direction.EAST.wall()
                        )
                    ) {
                        return true
                    }
                    if (currentX == tile.x + 1 && currentY <= tile.z && sizeY >= tile.z && !collisions.check(
                            currentX,
                            tile.z,
                            plane,
                            Direction.WEST.wall()
                        )
                    ) {
                        return true
                    }
                }
            }
            if (type == 2) {
                if (rotation == 0) {
                    if (currentX == tile.x - sizeXY && tile.z >= currentY && sizeY >= tile.z) {
                        return true
                    }
                    if (currentY == tile.z + 1 && tile.x in currentX..sizeX) {
                        return true
                    }
                    if (currentX == tile.x + 1 && currentY <= tile.z && sizeY >= tile.z && !collisions.check(
                            currentX,
                            tile.z,
                            plane,
                            Direction.WEST.wall()
                        )
                    ) {
                        return true
                    }
                    if (tile.z - sizeXY == currentY && tile.x in currentX..sizeX && !collisions.check(
                            tile.x,
                            sizeY,
                            plane,
                            Direction.NORTH.wall()
                        )
                    ) {
                        return true
                    }
                } else if (rotation == 1) {
                    if (currentX == tile.x - sizeXY && currentY <= tile.z && sizeY >= tile.z && !collisions.check(
                            sizeX,
                            tile.z,
                            plane,
                            Direction.EAST.wall()
                        )
                    ) {
                        return true
                    }
                    if (currentY == tile.z + 1 && tile.x in currentX..sizeX) {
                        return true
                    }
                    if (currentX == tile.x + 1 && currentY <= tile.z && sizeY >= tile.z) {
                        return true
                    }
                    if (currentY == tile.z - sizeXY && tile.x in currentX..sizeX && !collisions.check(
                            tile.x,
                            sizeY,
                            plane,
                            Direction.NORTH.wall()
                        )
                    ) {
                        return true
                    }
                } else if (rotation == 2) {
                    if (currentX == tile.x - sizeXY && tile.z >= currentY && tile.z <= sizeY && !collisions.check(
                            sizeX,
                            tile.z,
                            plane,
                            Direction.EAST.wall()
                        )
                    ) {
                        return true
                    }
                    if (currentY == tile.z + 1 && tile.x in currentX..sizeX && !collisions.check(
                            tile.x,
                            currentY,
                            plane,
                            Direction.SOUTH.wall()
                        )
                    ) {
                        return true
                    }
                    if (currentX == tile.x + 1 && currentY <= tile.z && sizeY >= tile.z) {
                        return true
                    }
                    if (currentY == tile.z - sizeXY && tile.x in currentX..sizeX) {
                        return true
                    }
                } else if (rotation == 3) {
                    if (currentX == tile.x - sizeXY && currentY <= tile.z && sizeY >= tile.z) {
                        return true
                    }
                    if (currentY == tile.z + 1 && tile.x in currentX..sizeX && !collisions.check(
                            tile.x,
                            currentY,
                            plane,
                            Direction.SOUTH.wall()
                        )
                    ) {
                        return true
                    }
                    if (currentX == tile.x + 1 && currentY <= tile.z && tile.z <= sizeY && !collisions.check(
                            currentX,
                            tile.z,
                            plane,
                            Direction.WEST.wall()
                        )
                    ) {
                        return true
                    }
                    if (currentY == tile.z - sizeXY && tile.x in currentX..sizeX) {
                        return true
                    }
                }
            }
            if (type == 9) {
                if (tile.x in currentX..sizeX && currentY == tile.z + 1 && !collisions.check(
                        tile.x,
                        currentY,
                        plane,
                        Direction.SOUTH.wall()
                    )
                ) {
                    return true
                }
                if (tile.x in currentX..sizeX && currentY == tile.z - sizeXY && !collisions.check(
                        tile.x,
                        sizeY,
                        plane,
                        Direction.NORTH.wall()
                    )
                ) {
                    return true
                }
                return if (currentX == tile.x - sizeXY && currentY <= tile.z && sizeY >= tile.z && !collisions.check(
                        sizeX,
                        tile.z,
                        plane,
                        Direction.EAST.wall()
                    )
                ) {
                    true
                } else currentX == tile.x + 1 && currentY <= tile.z && sizeY >= tile.z && !collisions.check(
                    currentX,
                    tile.z,
                    plane,
                    Direction.WEST.wall()
                )
            }
        }
        return false
    }

    companion object {
        fun Direction.wall() =
            flag() or CollisionFlag.WALL or CollisionFlag.BLOCKED
    }
}