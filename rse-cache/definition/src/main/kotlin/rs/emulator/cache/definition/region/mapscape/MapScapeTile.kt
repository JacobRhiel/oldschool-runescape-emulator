package rs.emulator.cache.definition.region.mapscape

/**
 *
 * @author Chk
 */
data class MapScapeTile(val x: Int,
                        val z: Int,
                        val plane: Int,
                        val attrOpcode: Int,
                        val settings: Byte,
                        val overlayId: Byte,
                        val overlayPath: Byte,
                        val overlayRotation: Byte,
                        val underlayId: Byte)
{

    class Builder
    {

        private var x: Int = 0

        private var z: Int = 0

        private var plane: Int = 0

        private var attribute: Int = 0

        private var settings: Byte = 0

        private var overlayId: Byte = 0

        private var overlayPath: Byte = 0

        private var overlayRotation: Byte = 0

        private var underlayId: Byte = 0

        fun coordinates(x: Int, z: Int) = apply { this.x = x }.apply { this.z = z }

        fun plane(plane: Int) = apply { this.plane = plane }

        fun attribute(attribute: Int) = apply { this.attribute = attribute }

        fun settings(settings: Byte) = apply { this.settings = settings }

        fun overlay(overlayId: Byte) = apply { this.overlayId = overlayId }

        fun underlay(underlayId: Byte) = apply { this.underlayId = underlayId }

        fun build() : MapScapeTile =
            MapScapeTile(
                x,
                z,
                plane,
                attribute,
                settings,
                overlayId,
                ((attribute - 2) / 4).toByte(),
                (attribute - 2 and 3).toByte(),
                underlayId
            )

    }

}