package rs.emulator.cache.definition.region.landscape

import rs.emulator.cache.definition.region.mapscape.MapScapeTile
import rs.emulator.definitions.Definition
import java.util.*

/**
 *
 * @author Chk
 */
data class LandscapeDefinition(val identifier: Int) : Definition(identifier)
{

    private val regionX = identifier shr 8

    private val regionY = identifier and 0xFF

    val tiles: Array<Array<Array<LandscapeTile?>>> = Array(64) { Array(64) { arrayOfNulls<LandscapeTile>(4) } }

}