package rs.emulator.cache.definition.region.landscape

/**
 *
 * @author Chk
 */
data class LandscapeTile(val id: Int = 0,
                         val type: Int = 0,
                         val orientation: Int = 0,
                         val localX: Int,
                         val localZ: Int,
                         val plane: Int)