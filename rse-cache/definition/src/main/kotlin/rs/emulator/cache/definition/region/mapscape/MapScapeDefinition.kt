package rs.emulator.cache.definition.region.mapscape

import rs.emulator.definitions.Definition

/**
 *
 * @author Chk
 */
class MapScapeDefinition(identifier: Int) : Definition(identifier)
{

    private val regionX: Int = identifier shr 8

    private val regionY: Int = identifier and 0xFF

    val tiles = Array(4) { Array(64) { arrayOfNulls<MapScapeTile>(64) } }

}