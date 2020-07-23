package rs.emulator.cache.definition.region.landscape

/**
 *
 * @author Chk
 */
data class LandscapeTile(val localX: Int,
                         val localZ: Int,
                         val plane: Int,
                         val locs: MutableList<LandscapeLoc> = mutableListOf()
)
{

    class Builder
    {

        private var localX: Int = 0

        private var localZ: Int = 0

        private var plane: Int = 0

        fun coordinates(x: Int, z: Int) = apply { this.localX = x }.apply { this.localZ = z }

        fun plane(plane: Int) = apply { this.plane = plane }

        fun build() : LandscapeTile =
            LandscapeTile(
                localX,
                localZ,
                plane
            )

    }

}