package rs.emulator.region

import kotlinx.coroutines.flow.MutableStateFlow
import rs.emulator.region.coordinate.Coordinate
import kotlin.reflect.KProperty

/**
 *
 * @author Chk
 */
class WorldCoordinate : Coordinate
{

    private val pointHash: Int

    constructor(x: Int, z: Int, plane: Int = 0) : this((x and 0x7FFF) or ((z and 0x7FFF) shl 15) or (plane shl 30))

    constructor(hash: Int) : super(hash and 0x7FFF, (hash shr 15) and 0x7FFF, hash ushr 30)
    {
        this.pointHash = hash
    }

    val as30BitInteger: Int get() = (y and 0x3FFF) or ((x and 0x3FFF) shl 14) or ((plane and 0x3) shl 28)

    val asTileHashMultiplier: Int get() = (y shr 13) or ((x shr 13) shl 8) or ((plane and 0x3) shl 16)

    override fun copy(offsetX: Int, offsetZ: Int, offsetPlane: Int): Coordinate = WorldCoordinate(this.x + offsetX, this.y + offsetZ, this.plane + plane)

    companion object
    {

        fun from30BitHash(packed: Int): WorldCoordinate
        {
            val x = ((packed shr 14) and 0x3FFF)
            val z = ((packed) and 0x3FFF)
            val height = (packed shr 28)
            return WorldCoordinate(x, z, height)
        }

        fun fromRegion(region: Int): WorldCoordinate
        {
            val x = ((region shr 8) shl 6)
            val z = ((region and 0xFF) shl 6)
            return WorldCoordinate(x, z)
        }

    }

}

val MutableStateFlow<WorldCoordinate>.x: Int get() = value.x
val MutableStateFlow<WorldCoordinate>.y: Int get() = value.y
val MutableStateFlow<WorldCoordinate>.plane: Int get() = value.plane
fun MutableStateFlow<WorldCoordinate>.set(coordinate: WorldCoordinate) {
    value = coordinate
}
fun MutableStateFlow<WorldCoordinate>.set(x: Int, y: Int) {
    value = WorldCoordinate(x, y)
}
operator fun MutableStateFlow<WorldCoordinate>.getValue(instance: Any?, property: KProperty<*>): WorldCoordinate {
    return value
}