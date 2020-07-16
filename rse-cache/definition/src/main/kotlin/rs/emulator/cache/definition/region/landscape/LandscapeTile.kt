package rs.emulator.cache.definition.region.landscape

/**
 *
 * @author Chk
 */
data class LandscapeTile(val id: Int = 0,
                         val types: MutableList<Int> = mutableListOf(),
                         val orientation: Int = 0,
                         val localX: Int,
                         val localZ: Int,
                         val plane: Int)
{

    class Builder
    {

        private var id: Int = 0

        private var localX: Int = 0

        private var localZ: Int = 0

        private var plane: Int = 0

        private var type: MutableList<Int> = mutableListOf()

        private var orientation: Int = 0

        fun id(id: Int) = apply { this.id = id }

        fun coordinates(x: Int, z: Int) = apply { this.localX = x }.apply { this.localZ = z }

        fun plane(plane: Int) = apply { this.plane = plane }

        fun type(type: Int) = apply { this.type.add(type) }

        fun orientation(orientation: Int) = apply { this.orientation = orientation }

        fun build() : LandscapeTile =
            LandscapeTile(
                id,
                type,
                orientation,
                localX,
                localZ,
                plane
            )

    }

}