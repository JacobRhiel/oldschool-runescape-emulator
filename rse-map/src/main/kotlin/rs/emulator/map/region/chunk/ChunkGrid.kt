package rs.emulator.map.region.chunk

import org.koin.core.KoinComponent
import org.koin.core.get
import rs.dusk.engine.model.world.map.collision.Collisions
import rs.dusk.engine.model.world.map.collision.add
import rs.emulator.cache.definition.region.landscape.LandscapeDefinition
import rs.emulator.map.grid.AreaGrid
import rs.emulator.map.grid.tile.GridTile
import rs.emulator.map.region.RegionGrid
import rs.emulator.region.RegionCoordinate
import rs.emulator.region.WorldCoordinate

/**
 *
 * @author Chk
 */
class ChunkGrid(val region: RegionGrid)
    : AreaGrid(width = 8, height = 8), KoinComponent
{


}