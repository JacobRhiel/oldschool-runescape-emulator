package rs.dusk.engine.path.target

import rs.dusk.engine.model.entity.Direction
import rs.dusk.engine.model.entity.Size
import rs.dusk.engine.model.world.map.collision.Collisions
import rs.dusk.engine.model.world.map.collision.check
import rs.dusk.engine.model.world.map.collision.flag
import rs.dusk.engine.path.TargetStrategy
import rs.emulator.region.coordinate.Coordinate

/**
 * Checks if within interact range of a targeted decoration
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since May 18, 2020
 */
data class DecorationTargetStrategy(
    private val collisions: Collisions,
    override val tile: Coordinate,
    override val size: Size,
    val rotation: Int,
    val type: Int
) : TargetStrategy {

    override fun reached(currentX: Int, currentY: Int, plane: Int, size: Size): Boolean {
        val sizeXY = size.width
        var rotation = rotation
        if (sizeXY == 1) {
            if (tile.x == currentX && currentY == tile.z) {
                return true
            }
        } else if (currentX <= tile.x && sizeXY + currentX - 1 >= tile.x && tile.z <= sizeXY + tile.z - 1) {
            return true
        }
        if (sizeXY == 1) {
            if (type == 6 || type == 7) {
                if (type == 7) {
                    rotation = rotation + 2 and 0x3
                }
                if (rotation == 0) {
                    if (currentX == tile.x + 1 && currentY == tile.z && !collisions.check(
                            currentX,
                            currentY,
                            plane,
                            Direction.WEST.flag()
                        )
                    ) {
                        return true
                    }
                    if (tile.x == currentX && currentY == tile.z - 1 && !collisions.check(
                            currentX,
                            currentY,
                            plane,
                            Direction.NORTH.flag()
                        )
                    ) {
                        return true
                    }
                } else if (rotation == 1) {
                    if (currentX == tile.x - 1 && currentY == tile.z && !collisions.check(
                            currentX,
                            currentY,
                            plane,
                            Direction.EAST.flag()
                        )
                    ) {
                        return true
                    }
                    if (tile.x == currentX && currentY == tile.z - 1 && !collisions.check(
                            currentX,
                            currentY,
                            plane,
                            Direction.NORTH.flag()
                        )
                    ) {
                        return true
                    }
                } else if (rotation == 2) {
                    if (currentX == tile.x - 1 && tile.z == currentY && !collisions.check(
                            currentX,
                            currentY,
                            plane,
                            Direction.EAST.flag()
                        )
                    ) {
                        return true
                    }
                    if (tile.x == currentX && currentY == tile.z + 1 && !collisions.check(
                            currentX,
                            currentY,
                            plane,
                            Direction.SOUTH.flag()
                        )
                    ) {
                        return true
                    }
                } else if (rotation == 3) {
                    if (tile.x + 1 == currentX && currentY == tile.z && !collisions.check(
                            currentX,
                            currentY,
                            plane,
                            Direction.WEST.flag()
                        )
                    ) {
                        return true
                    }
                    if (tile.x == currentX && currentY == tile.z + 1 && !collisions.check(
                            currentX,
                            currentY,
                            plane,
                            Direction.SOUTH.flag()
                        )
                    ) {
                        return true
                    }
                }
            }
            if (type == 8) {
                if (tile.x == currentX && currentY == tile.z + 1 && !collisions.check(
                        currentX,
                        currentY,
                        plane,
                        Direction.SOUTH.flag()
                    )
                ) {
                    return true
                }
                if (currentX == tile.x && tile.z - 1 == currentY && !collisions.check(
                        currentX,
                        currentY,
                        plane,
                        Direction.NORTH.flag()
                    )
                ) {
                    return true
                }
                return if (currentX == tile.x - 1 && tile.z == currentY && !collisions.check(
                        currentX,
                        currentY,
                        plane,
                        Direction.EAST.flag()
                    )
                ) {
                    true
                } else tile.x + 1 == currentX && tile.z == currentY && !collisions.check(
                    currentX,
                    currentY,
                    plane,
                    Direction.WEST.flag()
                )
            }
        } else {
            val sizeX = sizeXY + currentX - 1
            val sizeY = currentY + sizeXY - 1
            if (type == 6 || type == 7) {
                if (type == 7) {
                    rotation = rotation + 2 and 0x3
                }
                if (rotation == 0) {
                    if (currentX == tile.x + 1 && currentY <= tile.z && tile.z <= sizeY && !collisions.check(
                            currentX,
                            tile.z,
                            plane,
                            Direction.WEST.flag()
                        )
                    ) {
                        return true
                    }
                    if (tile.x in currentX..sizeX && currentY == tile.z - sizeXY && !collisions.check(
                            tile.x,
                            sizeY,
                            plane,
                            Direction.NORTH.flag()
                        )
                    ) {
                        return true
                    }
                } else if (rotation == 1) {
                    if (currentX == tile.x - sizeXY && tile.z >= currentY && tile.z <= sizeY && !collisions.check(
                            sizeX,
                            tile.z,
                            plane,
                            Direction.EAST.flag()
                        )
                    ) {
                        return true
                    }
                    if (tile.x in currentX..sizeX && currentY == tile.z - sizeXY && !collisions.check(
                            tile.x,
                            sizeY,
                            plane,
                            Direction.NORTH.flag()
                        )
                    ) {
                        return true
                    }
                } else if (rotation == 2) {
                    if (tile.x - sizeXY == currentX && tile.z >= currentY && tile.z <= sizeY && !collisions.check(
                            sizeX,
                            tile.z,
                            plane,
                            Direction.EAST.flag()
                        )
                    ) {
                        return true
                    }
                    if (tile.x in currentX..sizeX && tile.z + 1 == currentY && !collisions.check(
                            tile.x,
                            currentY,
                            plane,
                            Direction.SOUTH.flag()
                        )
                    ) {
                        return true
                    }
                } else if (rotation == 3) {
                    if (currentX == tile.x + 1 && currentY <= tile.z && tile.z <= sizeY && !collisions.check(
                            currentX,
                            tile.z,
                            plane,
                            Direction.WEST.flag()
                        )
                    ) {
                        return true
                    }
                    if (tile.x in currentX..sizeX && currentY == tile.z + 1 && !collisions.check(
                            tile.x,
                            currentY,
                            plane,
                            Direction.SOUTH.flag()
                        )
                    ) {
                        return true
                    }
                }
            }
            if (type == 8) {
                if (tile.x in currentX..sizeX && currentY == tile.z + 1 && !collisions.check(
                        tile.x,
                        currentY,
                        plane,
                        Direction.SOUTH.flag()
                    )
                ) {
                    return true
                }
                if (tile.x in currentX..sizeX && currentY == tile.z - sizeXY && !collisions.check(
                        tile.x,
                        sizeY,
                        plane,
                        Direction.NORTH.flag()
                    )
                ) {
                    return true
                }
                return if (currentX == tile.x - sizeXY && currentY <= tile.z && tile.z <= sizeY && !collisions.check(
                        sizeX,
                        tile.z,
                        plane,
                        Direction.EAST.flag()
                    )
                ) {
                    true
                } else currentX == tile.x + 1 && currentY <= tile.z && tile.z <= sizeY && !collisions.check(
                    currentX,
                    tile.z,
                    plane,
                    Direction.WEST.flag()
                )
            }
        }
        return false
    }
}