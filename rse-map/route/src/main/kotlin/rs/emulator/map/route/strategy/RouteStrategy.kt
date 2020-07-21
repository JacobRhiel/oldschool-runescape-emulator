package rs.emulator.map.route.strategy

import rs.emulator.map.MapGrid
import rs.emulator.map.collision.CollisionFlags
import rs.emulator.map.collision.CollisionMetrics
import rs.emulator.region.coordinate.Coordinate
import java.lang.StringBuilder
import kotlin.math.max
import kotlin.math.min

/**
 *
 * @author Chk
 */
abstract class RouteStrategy
{

    protected val metrics = CollisionMetrics()

    protected val GRAPH_SIZE = 128

    private val QUEUE_SIZE = GRAPH_SIZE * GRAPH_SIZE / 4

    private val ALTERNATIVE_ROUTE_MAX_DISTANCE = 100

    private val ALTERNATIVE_ROUTE_RANGE = 10

    private val directions = Array(GRAPH_SIZE) { IntArray(GRAPH_SIZE) }

    private val distances = Array(GRAPH_SIZE) { IntArray(GRAPH_SIZE) }

    private val clip = Array(GRAPH_SIZE) { IntArray(GRAPH_SIZE) }

    private val bufferX = IntArray(QUEUE_SIZE)

    private val bufferZ = IntArray(QUEUE_SIZE)

    abstract fun find(srcCoordinate: Coordinate, destCoordinate: Coordinate, entitySize: Int = 1, createAlternativeRoutes: Boolean = false) : String

    fun fetchCollisionData(graphSize: Int, map: MapGrid, srcCoordinate: Coordinate): String {

        val graphBaseX = srcCoordinate.x - graphSize / 2

        val graphBaseZ = srcCoordinate.z - graphSize / 2

        var rx = graphBaseX shr 6

        var rz = graphBaseZ shr 6

        val builder = StringBuilder()

        rx.until(graphBaseX + graphSize - 1 shr 6).forEach { x ->

            rz.until(graphBaseZ + graphSize - 1 shr 6).forEach { z ->

                val startX = max(graphBaseX, rx shl 6)

                val startZ = max(graphBaseZ, rz shl 6)

                val endX = min(graphBaseX + graphSize, (rx shl 6) + 64)

                val endZ = min(graphBaseZ + graphSize, (rz shl 6) + 64)

                val region = map.fetchRegion(rx shl 8 or rz)

                val chunks = region.grid.fetchChunks().values.toList()

                for(chunkX in startX until endX)
                {

                    for(chunkZ in startZ until endZ)
                    {
//(rx shr 3) + (regionZ shr 3) and plane

                        println("chunkx: ${(chunkX and 0x3F)} - chunkZ: ${(chunkZ and 0x3F)}")

                        val mask = chunks[((chunkX and 0x3F) shr chunkZ) and srcCoordinate.plane].tiles[((chunkX and 0x3F) shr 3) + ((chunkZ and 0x3F) shr 3) and srcCoordinate.plane]?.types?.sumBy { it }!!

                       // println("chunk mask: $mask")

                        clip[chunkX - graphBaseX][chunkZ - graphBaseZ] = chunks[((chunkX and 0x3F) shr chunkZ) and srcCoordinate.plane].tiles[((chunkX and 0x3F) shr 3) + ((chunkZ and 0x3F) shr 3) and srcCoordinate.plane]?.types?.sumBy { it }!!

                    }

                }

                rz++

            }

            rx++

        }

        return builder.toString()

    }

    fun calculatePath(entitySize: Int = 1, srcCoordinate: Coordinate)
    {

        val _directions = directions

        val _distances = distances

        val _clip = clip

        val _bufferX = bufferX

        val _bufferZ = bufferZ

        var currentX = srcCoordinate.x

        var currentZ = srcCoordinate.z

        val graphBaseX = currentX - GRAPH_SIZE / 2

        val graphBaseZ = currentZ - GRAPH_SIZE / 2

        var currentGraphX = currentX - graphBaseX

        var currentGraphZ = currentZ - graphBaseZ

        _distances[currentGraphX][currentGraphZ] = 0

        _directions[currentGraphX][currentGraphZ] = 99

        var read = 0

        var write = 0

        _bufferX[write] = currentX

        _bufferZ[write++] = currentZ

        while(read != write)
        {

            currentX = _bufferX[read]

            currentZ = _bufferZ[read]

            read = read + 1 and QUEUE_SIZE - 1

            currentGraphX = currentX - graphBaseX

            currentGraphZ = currentZ - graphBaseZ

            //todo: following

            //todo: check collision metrics for end path

            val nextDistance = _distances[currentGraphX][currentGraphZ] + 1

            if(currentGraphX > 0 && _directions[currentGraphX - 1][currentGraphZ] == 0 && (_clip[currentGraphX - 1][currentGraphZ] and (CollisionFlags.BLOCK_TILE_COMPLETE.flag) == 0))
            {

                _bufferX[write] = currentX - 1

                _bufferZ[write] = currentZ

                write = write + 1 and QUEUE_SIZE - 1

                _directions[currentGraphX - 1][currentGraphZ] = 0//east

                _distances[currentGraphX - 1][currentGraphZ] = nextDistance

            }

        }

    }

}