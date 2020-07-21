package rs.emulator.region.zones

import rs.emulator.reactive.ReactiveZone

/**
 *
 * @author javatar
 */

class RegionZone(
    val bottomLeftX: Int,
    val bottomLeftZ: Int,
    val plane: Int = 0,
    val width: Int = 1,
    val height: Int = 1,
    val reactiveZone: ReactiveZone<ZoneEvent<*>> = ReactiveZone(
        bottomLeftX,
        bottomLeftZ,
        plane,
        width,
        height
    )
)