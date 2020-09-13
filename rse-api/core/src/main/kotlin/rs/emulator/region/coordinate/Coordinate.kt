package rs.emulator.region.coordinate

import com.google.gson.Gson
import rs.emulator.region.RegionCoordinate
import rs.emulator.region.WorldCoordinate
import rs.emulator.region.chunk.ChunkCoordinate
import rs.emulator.utilities.koin.get

/**
 *
 * @author Chk
 */
abstract class Coordinate(var x: Int, var y: Int, var plane: Int = 0)
{

    constructor(hash: Int) : this(hash and 0x7FFF, (hash shr 15) and 0x7FFF, hash ushr 30)

    open fun toLocal() : ChunkCoordinate = ChunkCoordinate((x shr 6) shl 8 and y shr 6, x and 63, y and 63, plane)

    open fun toRegion() : RegionCoordinate = RegionCoordinate(x shr 6, y shr 6, plane)

    open fun toWorld() : WorldCoordinate = WorldCoordinate(x, y, plane)

    fun transform(x: Int, z: Int, plane: Int) = WorldCoordinate(this.x + x, this.y + z, this.plane + plane)

    fun transform(x: Int, z: Int): WorldCoordinate = WorldCoordinate(this.x + x, this.y + z, this.plane)

    fun transform(plane: Int): WorldCoordinate = WorldCoordinate(this.x, this.y, this.plane + plane)

    fun set(coordinate: WorldCoordinate) = this.apply { this.x = coordinate.x }.apply { this.y = coordinate.y }.apply { this.plane = coordinate.plane }

    fun set(x: Int, z: Int, plane: Int) = this.apply { this.x = x }.apply { this.y = z }.apply { this.plane = plane }

    fun set(x: Int, z: Int) = this.apply { this.x = x }.apply { this.y = z }

    fun add(x: Int = 0, y: Int = 0, plane: Int = 0) = copy(offsetX = x, offsetZ = y, offsetPlane = plane)
    
    fun equals(x: Int = 0, y: Int = 0, plane: Int = 0) = this.x == x && this.y == y && this.plane == plane
    
    fun minus(x: Int = 0, y: Int = 0, plane: Int = 0) = add(-x, -y, plane)
    
    fun delta(x: Int = 0, y: Int = 0, plane: Int = 0) = minus(x, y, plane)

    fun add(point: Coordinate) = add(point.x, point.y, point.plane)
    
    fun minus(point: Coordinate) = minus(point.x, point.y, point.plane)
    
    fun delta(point: Coordinate) = delta(point.x, point.y, point.plane)

    abstract fun copy(offsetX: Int = 0, offsetZ: Int = 0, offsetPlane: Int = 0): Coordinate

    override fun toString(): String = get<Gson>().toJson(this)

    override fun equals(other: Any?): Boolean
    {
        if(other == null) return false
        val otherCoordinate = other as Coordinate
        return this.x == otherCoordinate.x && this.y == otherCoordinate.y && this.plane == otherCoordinate.plane
    }

    companion object
    {

        fun getId(x: Int, z: Int, plane: Int = 0) = (z and 0x3FFF) or ((x and 0x3FFF) shl 14) or ((plane and 0x3) shl 28)

        fun fromRotatedHash(packed: Int): WorldCoordinate {
            val x = ((packed shr 14) and 0x3FF) shl 3
            val z = ((packed shr 3) and 0x7FF) shl 3
            val height = (packed shr 28) and 0x3
            return WorldCoordinate(x, z, height)
        }

        fun from30BitHash(packed: Int): WorldCoordinate {
            val x = ((packed shr 14) and 0x3FFF)
            val z = ((packed) and 0x3FFF)
            val height = (packed shr 28)
            return WorldCoordinate(x, z, height)
        }

        fun fromRegion(region: Int): WorldCoordinate {
            val x = ((region shr 8) shl 6)
            val z = ((region and 0xFF) shl 6)
            return WorldCoordinate(x, z)
        }

        val EMPTY = WorldCoordinate(0)

    }

}