package rs.dusk.engine.model.world.map.collision

import rs.emulator.region.coordinate.Coordinate

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 16, 2020
 */
data class Collisions(val delegate: MutableMap<Int, Int> = mutableMapOf()) : MutableMap<Int, Int> by delegate

fun Collisions.add(x: Int, y: Int, plane: Int, flag: Int) {
    val tile = Coordinate.getId(x, y, plane)
    val value = get(tile) ?: 0
    this[tile] = value or flag
}

operator fun Collisions.set(x: Int, y: Int, plane: Int, flag: Int) {
    this[Coordinate.getId(x, y, plane)] = flag
}

fun Collisions.remove(x: Int, y: Int, plane: Int, flag: Int) {
    val tile = Coordinate.getId(x, y, plane)
    val value = get(tile) ?: 0
    this[tile] = value and flag.inv()
}

operator fun Collisions.get(x: Int, y: Int, plane: Int): Int
{
    val value = this[Coordinate.getId(x, y, plane)] ?: 0
    println("val for hash: $x, $y, $plane, $value")
    return value
}

fun Collisions.check(x: Int, y: Int, plane: Int, flag: Int): Boolean
{

    println(this[x, y, plane] and flag)

    return this[x, y, plane] and flag != 0
}