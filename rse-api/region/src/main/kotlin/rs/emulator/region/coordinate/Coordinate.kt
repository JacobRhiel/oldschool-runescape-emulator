package rs.emulator.region.coordinate

import rs.emulator.region.RegionCoordinate
import rs.emulator.region.WorldCoordinate
import rs.emulator.region.chunk.ChunkCoordinate

/**
 *
 * @author Chk
 */
abstract class Coordinate(var x: Int, var z: Int, var plane: Int = 0)
{

    open fun toLocal() : ChunkCoordinate = ChunkCoordinate((x shr 6) shl 8 and z shr 6, x and 63, z and 63, plane)

    open fun toRegion() : RegionCoordinate = RegionCoordinate(x shr 6, z shr 6, plane)

    open fun toWorld() : WorldCoordinate = WorldCoordinate(x, z, plane)

    fun transform(x: Int, z: Int, plane: Int) = WorldCoordinate(this.x + x, this.z + z, this.plane + plane)

    fun transform(x: Int, z: Int): WorldCoordinate = WorldCoordinate(this.x + x, this.z + z, this.plane)

    fun transform(plane: Int): WorldCoordinate = WorldCoordinate(this.x, this.z, this.plane + plane)

    fun set(coordinate: WorldCoordinate) = this.apply { this.x = coordinate.x }.apply { this.z = coordinate.z }.apply { this.plane = coordinate.plane }

    fun set(x: Int, z: Int, plane: Int) = this.apply { this.x = x }.apply { this.z = z }.apply { this.plane = plane }

    fun set(x: Int, z: Int) = this.apply { this.x = x }.apply { this.z = z }

    override fun toString(): String = "x: $x z: $z : plane $plane"

}