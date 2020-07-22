package rs.emulator.region.chunk

import rs.emulator.region.RegionCoordinate
import rs.emulator.region.WorldCoordinate
import rs.emulator.region.coordinate.Coordinate

/**
 *
 * @author Chk
 */
class ChunkCoordinate(private val region: Int, x: Int, z: Int, plane: Int = 0) : Coordinate(x, z, plane)
{

    override fun toRegion(): RegionCoordinate =
        RegionCoordinate((region shr 8) * 64, (region and 0xff) * 64, plane)

    //(x shr 6) shl 8 and z shr 6, x and 63, z and 63
    override fun toWorld() = WorldCoordinate(x + ((region shr 8) shl 6), z + ((region and 0xFF) shl 6), plane)

    override fun copy(offsetX: Int, offsetZ: Int, offsetPlane: Int): Coordinate = ChunkCoordinate(this.x + offsetX, this.z + offsetZ, this.plane + plane)

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int = (x shl 16) or z

}