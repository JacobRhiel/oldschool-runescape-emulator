package rs.emulator.map.region

import com.google.common.collect.ArrayListMultimap
import rs.dusk.engine.model.entity.Direction
import rs.dusk.engine.model.world.map.collision.*
import rs.dusk.engine.model.world.map.collision.CollisionFlag.FLOOR
import rs.emulator.cache.definition.definition
import rs.emulator.cache.definition.region.landscape.LandscapeDefinition
import rs.emulator.cache.definition.region.landscape.LandscapeLoc
import rs.emulator.cache.definition.region.mapscape.MapScapeDefinition
import rs.emulator.cache.definition.region.mapscape.MapScapeTile
import rs.emulator.definitions.entity.loc.LocDefinition
import rs.emulator.encryption.xtea.XteaKeyService
import rs.emulator.map.grid.AreaGrid
import rs.emulator.map.grid.tile.GridTile
import rs.emulator.map.region.chunk.ChunkGrid
import rs.emulator.region.WorldCoordinate
import rs.emulator.region.as30BitInteger
import rs.emulator.utilities.koin.get

/**
 *
 * @author Chk
 */
class RegionGrid(val id: Int) : AreaGrid(width = 64, height = 64) {

    private val mapScapeDefinition: MapScapeDefinition = definition().find(id)

    private val landscapeDefinition: LandscapeDefinition = definition().find(id, XteaKeyService.get(id))

    private val collisions: Collisions = get()

    private val chunks = mutableMapOf<Int, ChunkGrid>()

    private val tiles = mutableMapOf<Int, GridTile>()

    val regionStartTile = WorldCoordinate(((id shr 8) and 0xFF) shl 6, (id and 0xFF) shl 6, 0)

    fun fetchChunkGrid(id: Int) = chunks.computeIfAbsent(id) { ChunkGrid(this) }

    fun fetchChunkGrid(x: Int, z: Int, height: Int) = chunks.computeIfAbsent((x shr z) and height) { ChunkGrid(this) }

    fun fetchChunks() = chunks.toMap()

    override fun constructGrid() {

        val plottedX = ArrayListMultimap.create<Int, Int>()

        val plottedZ = ArrayListMultimap.create<Int, Int>()

        for (plane in 0 until levels) {

            for (rx in 0 until width) {

                for (ry in 0 until height) {

                    val worldCoordinate = regionStartTile.add(rx, ry) as WorldCoordinate

                    val landscapeTile = landscapeDefinition.tiles[plane][rx][ry]

                    if (landscapeTile != null) {

                        tiles.computeIfAbsent(worldCoordinate.as30BitInteger) {

                            GridTile(
                                landscapeTile.localX,
                                landscapeTile.localZ,
                                landscapeTile.plane,
                                landscapeTile.locs
                            )

                        }

                    }

                    val mapScapeTile = mapScapeDefinition.tiles[plane][rx][ry]

                    if (mapScapeTile != null) {

                        val blocked = isTile(mapScapeTile, BLOCKED_TILE)

                        val bridge = isTile(mapScapeDefinition.tiles[1][rx][ry]!!, BRIDGE_TILE)

                        if (blocked && !bridge)
                            collisions.add(worldCoordinate.x, worldCoordinate.y, plane, FLOOR)

                    }

                    landscapeTile?.locs?.forEach {
                        modifyCollision(worldCoordinate, it, ADD_MASK)
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

    fun modifyCollision(location: WorldCoordinate, loc: LandscapeLoc, changeType: Int) {

        val definition: LocDefinition = definition().find(loc.id)

        when (loc.type) {
            in 0..3 -> {
                if (definition.solidType != 0) {
                    modifyWall(location, loc, changeType)
                }
            }
            in 9..21 -> {
                if (definition.solidType != 0) {
                    modifyObject(location, loc, changeType)
                }
            }
            22 -> {
                if (definition.solidType == 1) {
                    modifyMask(location.x, location.y, location.plane, CollisionFlag.FLOOR_DECO, changeType)
                }
            }
        }
    }

    fun modifyObject(location: WorldCoordinate, loc: LandscapeLoc, changeType: Int) {

        val definition: LocDefinition = definition().find(loc.id)

        var mask = 0x100
        if (definition.projectileClipped) {
            mask += 0x20000
        }

        var sizeX = definition.sizeX
        var sizeY = definition.sizeY

        if (loc.orientation == 1 || loc.orientation == 3) {
            sizeX = definition.sizeY
            sizeY = definition.sizeX
        }

        for (var7 in location.x until location.x + sizeX) {
            if (var7 in 0..103) {
                for (var8 in location.y until location.y + sizeY) {
                    if (var8 in 0..103) {
                        modifyMask(var7, var8, location.plane, mask, changeType)
                    }
                }
            }
        }
        /*
        for (offsetX in 0 until width)
            for (offsetY in 0 until height)
                modifyMask(location.x + offsetX, location.y + offsetY, location.plane, mask, changeType)*/

    }

    fun modifyWall(location: WorldCoordinate, loc: LandscapeLoc, changeType: Int) {
        val rotation = loc.orientation and 3
        val type = loc.type
        val def: LocDefinition = definition().find(loc.id)
        handleWallFlags(type, rotation, location, changeType)
        if (def.projectileClipped) {
            handleProjectileClippedWalls(type, rotation, location, changeType)
        }
        /*val definition: LocDefinition = definition().find(loc.id)
               if (definition.projectileClipped)
                   modifyWall(location, loc, 1, changeType)

               if (!definition.isSolid)
                   modifyWall(location, loc, 2, changeType)*/
    }

    private fun handleWallFlags(
        type: Int,
        rotation: Int,
        tile: WorldCoordinate,
        changeType: Int
    ) {
        when (type) {

            0 -> {

                when (rotation) {

                    0 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x80, changeType)
                        modifyMask(tile.x - 1, tile.y, tile.plane, 0x8, changeType)
                    }
                    1 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x2, changeType)
                        modifyMask(tile.x, tile.y + 1, tile.plane, 0x20, changeType)
                    }
                    2 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x8, changeType)
                        modifyMask(tile.x + 1, tile.y, tile.plane, 0x80, changeType)
                    }
                    3 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x20, changeType)
                        modifyMask(tile.x, tile.y - 1, tile.plane, 0x2, changeType)
                    }

                }

            }
            1, 3 -> {

                when (rotation) {

                    0 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x1, changeType)
                        modifyMask(tile.x - 1, tile.y + 1, tile.plane, 0x10, changeType)
                    }
                    1 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x4, changeType)
                        modifyMask(tile.x + 1, tile.y + 1, tile.plane, 0x40, changeType)
                    }
                    2 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x10, changeType)
                        modifyMask(tile.x + 1, tile.y - 1, tile.plane, 0x1, changeType)
                    }
                    3 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x40, changeType)
                        modifyMask(tile.x - 1, tile.y - 1, tile.plane, 0x4, changeType)
                    }

                }
            }
            2 -> {

                when (rotation) {

                    0 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x82, changeType)
                        modifyMask(tile.x - 1, tile.y, tile.plane, 0x8, changeType)
                        modifyMask(tile.x, tile.y + 1, tile.plane, 0x20, changeType)
                    }
                    1 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0xa, changeType)
                        modifyMask(tile.x, tile.y + 1, tile.plane, 0x20, changeType)
                        modifyMask(tile.x + 1, tile.y, tile.plane, 0x80, changeType)
                    }
                    2 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x28, changeType)
                        modifyMask(tile.x + 1, tile.y, tile.plane, 0x80, changeType)
                        modifyMask(tile.x, tile.y - 1, tile.plane, 0x2, changeType)
                    }
                    3 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0xa0, changeType)
                        modifyMask(tile.x, tile.y - 1, tile.plane, 0x2, changeType)
                        modifyMask(tile.x - 1, tile.y, tile.plane, 0x8, changeType)
                    }
                }

            }

        }
    }

    private fun handleProjectileClippedWalls(
        type: Int,
        rotation: Int,
        tile: WorldCoordinate,
        changeType: Int
    ) {
        when (type) {

            0 -> {

                when (rotation) {

                    0 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x10000, changeType)
                        modifyMask(tile.x - 1, tile.y, tile.plane, 0x1000, changeType)
                    }
                    1 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x400, changeType)
                        modifyMask(tile.x, tile.y + 1, tile.plane, 0x4000, changeType)
                    }
                    2 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x1000, changeType)
                        modifyMask(tile.x + 1, tile.y, tile.plane, 0x10000, changeType)
                    }
                    3 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x4000, changeType)
                        modifyMask(tile.x, tile.y - 1, tile.plane, 0x400, changeType)
                    }

                }

            }
            1, 3 -> {

                when (rotation) {

                    0 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x200, changeType)
                        modifyMask(tile.x - 1, tile.y + 1, tile.plane, 0x2000, changeType)
                    }
                    1 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x800, changeType)
                        modifyMask(tile.x + 1, tile.y + 1, tile.plane, 0x8000, changeType)
                    }
                    2 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x2000, changeType)
                        modifyMask(tile.x + 1, tile.y - 1, tile.plane, 0x200, changeType)
                    }
                    3 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x8000, changeType)
                        modifyMask(tile.x - 1, tile.y - 1, tile.plane, 0x800, changeType)
                    }

                }
            }
            2 -> {

                when (rotation) {

                    0 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x10400, changeType)
                        modifyMask(tile.x - 1, tile.y, tile.plane, 0x1000, changeType)
                        modifyMask(tile.x, tile.y + 1, tile.plane, 0x4000, changeType)
                    }
                    1 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x1400, changeType)
                        modifyMask(tile.x, tile.y + 1, tile.plane, 0x4000, changeType)
                        modifyMask(tile.x + 1, tile.y, tile.plane, 0x10000, changeType)
                    }
                    2 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x5000, changeType)
                        modifyMask(tile.x + 1, tile.y, tile.plane, 0x10000, changeType)
                        modifyMask(tile.x, tile.y - 1, tile.plane, 0x400, changeType)
                    }
                    3 -> {
                        modifyMask(tile.x, tile.y, tile.plane, 0x14000, changeType)
                        modifyMask(tile.x, tile.y - 1, tile.plane, 0x400, changeType)
                        modifyMask(tile.x - 1, tile.y, tile.plane, 0x1000, changeType)
                    }
                }

            }

        }

        /*if (projectileClipped) {
            if (type == 0) {
                if (rotation == 0) {
                    this.setFlag(x, y, 0x10000)
                    this.setFlag(x - 1, y, 0x1000)
                }
                if (rotation == 1) {
                    this.setFlag(x, y, 0x400)
                    this.setFlag(x, y + 1, 0x4000)
                }
                if (rotation == 2) {
                    this.setFlag(x, y, 0x1000)
                    this.setFlag(x + 1, y, 0x10000)
                }
                if (rotation == 3) {
                    this.setFlag(x, y, 0x4000)
                    this.setFlag(x, y - 1, 0x400)
                }
            }
            if (type == 1 || type == 3) {
                if (rotation == 0) {
                    this.setFlag(x, y, 0x200)
                    this.setFlag(x - 1, y + 1, 0x2000)
                }
                if (rotation == 1) {
                    this.setFlag(x, y, 0x800)
                    this.setFlag(x + 1, y + 1, 0x8000)
                }
                if (rotation == 2) {
                    this.setFlag(x, y, 0x2000)
                    this.setFlag(x + 1, y - 1, 0x200)
                }
                if (rotation == 3) {
                    this.setFlag(x, y, 0x8000)
                    this.setFlag(x - 1, y - 1, 0x800)
                }
            }
            if (type == 2) {
                if (rotation == 0) {
                    this.setFlag(x, y, 0x10400)
                    this.setFlag(x - 1, y, 0x1000)
                    this.setFlag(x, y + 1, 0x4000)
                }
                if (rotation == 1) {
                    this.setFlag(x, y, 0x1400)
                    this.setFlag(x, y + 1, 0x4000)
                    this.setFlag(x + 1, y, 0x10000)
                }
                if (rotation == 2) {
                    this.setFlag(x, y, 0x5000)
                    this.setFlag(x + 1, y, 0x10000)
                    this.setFlag(x, y - 1, 0x400)
                }
                if (rotation == 3) {
                    this.setFlag(x, y, 0x14000)
                    this.setFlag(x, y - 1, 0x400)
                    this.setFlag(x - 1, y, 0x1000)
                }
            }
        }*/


    }

    val ADD_MASK = 0
    val REMOVE_MASK = 1
    val SET_MASK = 2

    fun modifyMask(x: Int, y: Int, plane: Int, mask: Int, changeType: Any) {
        when (changeType) {
            ADD_MASK -> collisions.add(x, y, plane, mask)
            REMOVE_MASK -> collisions.remove(x, y, plane, mask)
            SET_MASK -> collisions[x, y, plane] = mask
        }
    }

    fun applyMotion(mask: Int, motion: Int): Int {
        return when (motion) {
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