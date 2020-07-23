package rs.emulator.map.region

import com.google.common.collect.ArrayListMultimap
import rs.dusk.engine.model.entity.Direction
import rs.dusk.engine.model.world.map.collision.*
import rs.dusk.engine.model.world.map.collision.CollisionFlag.FLOOR
import rs.emulator.cache.definition.definition
import rs.emulator.cache.definition.region.landscape.*
import rs.emulator.cache.definition.region.mapscape.MapScapeDefinition
import rs.emulator.cache.definition.region.mapscape.MapScapeTile
import rs.emulator.definitions.entity.loc.LocDefinition
import rs.emulator.encryption.xtea.XteaKeyService
import rs.emulator.map.grid.AreaGrid
import rs.emulator.map.grid.tile.GridTile
import rs.emulator.map.region.chunk.ChunkGrid
import rs.emulator.region.RegionCoordinate
import rs.emulator.region.WorldCoordinate
import rs.emulator.utilities.koin.get

/**
 *
 * @author Chk
 */
class RegionGrid(val id: Int)
    : AreaGrid(width = 64, height = 64)
{

    private val mapScapeDefinition: MapScapeDefinition = definition().find(id)

    private val landscapeDefinition: LandscapeDefinition = definition().find(id, XteaKeyService.get(id))

    private val collisions: Collisions = get()

    private val chunks = mutableMapOf<Int, ChunkGrid>()

    private val tiles = mutableMapOf<Int, GridTile>()

    val regionStartTile = WorldCoordinate(((id shr 8) and 0xFF) shl 6, (id and 0xFF) shl 6, 0)

    fun fetchChunkGrid(id: Int) = chunks.computeIfAbsent(id) { ChunkGrid(this) }

    fun fetchChunkGrid(x: Int, z: Int, height: Int) = chunks.computeIfAbsent((x shr z) and height) { ChunkGrid(this) }

    fun fetchChunks() = chunks.toMap()

    override fun constructGrid()
    {

        val plottedX = ArrayListMultimap.create<Int, Int>()

        val plottedZ = ArrayListMultimap.create<Int, Int>()

        for(plane in 0 until levels)
        {

            for(rx in 0 until width)
            {

                for(ry in 0 until height)
                {

                    val worldCoordinate = regionStartTile.add(rx, ry) as WorldCoordinate

                    val landscapeTile = landscapeDefinition.tiles[plane][rx][ry]

                    if(landscapeTile != null)
                    {

                        tiles.computeIfAbsent(worldCoordinate.as30BitInteger) {

                            GridTile(landscapeTile.localX, landscapeTile.localZ, landscapeTile.plane, landscapeTile.locs)

                        }

                    }

                    val mapScapeTile = mapScapeDefinition.tiles[plane][rx][ry]

                    if(mapScapeTile != null)
                    {

                        val blocked = isTile(mapScapeTile, BLOCKED_TILE)

                        val bridge = isTile(mapScapeTile, BRIDGE_TILE)

                        if (blocked && !bridge)
                            collisions.add(worldCoordinate.x, worldCoordinate.z, plane, FLOOR)

                    }

                    if(worldCoordinate.x == 3223 && worldCoordinate.z == 3217)
                    {
                        println("pringint locs")
                        println("locs: " + landscapeTile?.locs?.toTypedArray()?.contentDeepToString())
                    }

                    landscapeTile?.locs?.firstOrNull { it.id == 10778 }?.apply {
                        println("adding collision to obj.")
                        modifyCollision(worldCoordinate, this, ADD_MASK)
                    }

                }

            }

            /*for (x in 0 until width)
                if (x % 8 == 0)
                    plottedX.put(plane, if(x == 64) 63 else x)

            for (z in height downTo 0)
                if (z % 8 == 0)
                    plottedZ.put(plane, if(z == 64) 63 else z)

            plottedX[plane].forEach { x ->

                plottedZ[plane].forEach { z ->

                    val chunkGrid = fetchChunkGrid(x, z, plane)

                    chunkGrid.constructGrid(x, z, plane, landscapeDefinition)

                }

            }*/

        }

    }

    fun modifyCollision(location: WorldCoordinate, loc: LandscapeLoc, changeType: Int)
    {

        val definition: LocDefinition = definition().find(loc.id)

        if (!definition.solid)
            return

        when (loc.type)
        {
            in 0..3 -> modifyWall(location, loc, changeType)
            in 9..21 -> modifyObject(location, loc, changeType)
            22 -> {
                if (definition.solid) {
                    modifyMask(location.x, location.z, location.plane, CollisionFlag.FLOOR_DECO, changeType)
                }
            }
        }
    }

    fun modifyObject(location: WorldCoordinate, loc: LandscapeLoc, changeType: Int)
    {

        var mask = CollisionFlag.LAND

        val definition: LocDefinition = definition().find(loc.id)

        if (definition.obstructive) //solid
            mask = mask or CollisionFlag.SKY

        if (!definition.solid) //not alt
            mask = mask or CollisionFlag.IGNORED

        var width = definition.width
        var height = definition.length

        if (loc.orientation and 0x1 == 1)
        {
            width = definition.length
            height = definition.width
        }

        for (offsetX in 0 until width)
            for (offsetY in 0 until height)
                modifyMask(location.x + offsetX, location.z + offsetY, location.plane, mask, changeType)

    }


    fun modifyWall(location: WorldCoordinate, loc: LandscapeLoc, changeType: Int) {
        modifyWall(location, loc, 0, changeType)
        val definition: LocDefinition = definition().find(loc.id)
        if (definition.impenetrable)
            modifyWall(location, loc, 1, changeType)

        if (!definition.solid)
            modifyWall(location, loc, 2, changeType)

    }

    /**
     * Wall types:
     * 0 - ║ External wall (vertical or horizontal)
     * 1 - ╔ External corner (flat/missing)
     * 2 - ╝ Internal corner
     * 3 - ╔ External corner (regular)
     */
    fun modifyWall(location: WorldCoordinate, loc: LandscapeLoc, motion: Int, changeType: Int)
    {

        val rotation = loc.orientation
        val type = loc.type
        var tile = RegionCoordinate(location.x, location.z)

        // Internal corners
        if (type == 2)
        {
            // Mask both cardinal directions
            val or = when (Direction.ordinal[rotation and 0x3])
            {
                Direction.NORTH_WEST -> CollisionFlag.NORTH_OR_WEST
                Direction.NORTH_EAST -> CollisionFlag.NORTH_OR_EAST
                Direction.SOUTH_EAST -> CollisionFlag.SOUTH_OR_EAST
                Direction.SOUTH_WEST -> CollisionFlag.SOUTH_OR_WEST
                else                 -> 0
            }
            modifyMask(location.x, location.z, location.plane, applyMotion(or, motion), changeType)
            tile = tile.add(Direction.cardinal[(rotation + 3) and 0x3].delta) as RegionCoordinate
        }

        // Mask one wall side
        var direction = when (type)
        {
            0 -> Direction.cardinal[(rotation + 3) and 0x3]
            2 -> Direction.cardinal[(rotation + 1) and 0x3]
            else -> Direction.ordinal[rotation and 0x3]
        }

        modifyMask(tile.x, tile.z, tile.plane, direction.flag(motion), changeType)

        // Mask other wall side
        tile = if (type == 2)
            tile.add(Direction.cardinal[rotation and 0x3].delta) as RegionCoordinate
        else
            tile.add(direction.delta) as RegionCoordinate

        direction = when (type)
        {
            2 -> Direction.cardinal[(rotation + 2) and 0x3]
            else -> direction.inverse()
        }

        modifyMask(tile.x, tile.z, tile.plane, direction.flag(motion), changeType)

    }

    val ADD_MASK = 0
    val REMOVE_MASK = 1
    val SET_MASK = 2

    fun modifyMask(x: Int, y: Int, plane: Int, mask: Int, changeType: Any)
    {
        when (changeType)
        {
            ADD_MASK -> collisions.add(x, y, plane, mask)
            REMOVE_MASK -> collisions.remove(x, y, plane, mask)
            SET_MASK -> collisions[x, y, plane] = mask
        }
    }

    fun applyMotion(mask: Int, motion: Int): Int
    {
        return when (motion)
        {
            1 -> mask shl 9
            2 -> mask shl 22
            else -> mask
        }
    }

    fun Direction.flag(motion: Int) = applyMotion(flag(), motion)

    fun isTile(loc: MapScapeTile, flag: Int) = loc.settings.toInt() and flag == flag

    val BLOCKED_TILE = 0x1
    val BRIDGE_TILE = 0x2

}