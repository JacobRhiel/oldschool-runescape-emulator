package rs.emulator.cache.definition.region.landscape

import rs.emulator.cache.definition.Definition
import java.util.*

/**
 *
 * @author Chk
 */
data class LandscapeDefinition(val identifier: Int,
                               val tiles: ArrayList<LandscapeTile> = arrayListOf()

) : Definition(identifier)
{

    private val regionX = identifier shr 8

    private val regionY = identifier and 0xFF

}