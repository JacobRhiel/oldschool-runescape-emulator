package rs.emulator.cache.definition.region.landscape

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.definition.generator.DefinitionGenerator
import rs.emulator.cache.definition.region.mapscape.MapScapeDefinition
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.utilities.koin.get

/**
 *
 * @author Chk
 */
class LandscapeDefinitionGenerator : DefinitionGenerator<LandscapeDefinition>()
{

    override val definitionClass: Class<LandscapeDefinition> = LandscapeDefinition::class.java

    override val indexConfig: IndexConfig = IndexConfig.MAPS

    override val archive: Int = -1

    override val namedArchive: Boolean = true

    override fun generate(id: Int, reader: BufferedReader): LandscapeDefinition = LandscapeDefinition(id)

    override fun generateArchiveName(id: Int): String = "l${(id shr 8)}_${(id and 0xFF)}"

    override fun decodeHeader(id: Int, reader: BufferedReader): LandscapeDefinition
    {
        val definition = generate(id, reader)
        decode(definition, -1, reader)
        return definition
    }

    override fun decode(definition: LandscapeDefinition, opcode: Int, reader: BufferedReader)
    {
        var id = -1

        var idOffset: Int

        while (reader.unsignedIntSmartShortCompat.also { idOffset = it } != 0)
        {
            id += idOffset
            var position = 0
            var positionOffset: Int

            while (reader.unsignedSmart.also { positionOffset = it } != 0)
            {
                position += positionOffset - 1
                val localY = position and 0x3F
                val localX = position shr 6 and 0x3F
                var plane = position shr 12 and 0x3
                val attributes: Int = reader.getUnsigned(DataType.BYTE).toInt()
                val type = attributes shr 2
                val orientation = attributes and 0x3

                // Validate region
                if (localX < 0 || localX > 64 || localY < 0 || localY >= 64) {
                    continue
                }

                val mapScapeDefinition: MapScapeDefinition = rs.emulator.cache.definition.definition().find(definition.id)

                val settings = mapScapeDefinition.tiles[1][localX][localY]!!.settings.toInt()

                val bridge = settings and 0x2 == 0x2

                // Decrease bridges
                if (bridge)
                    plane--

                // Validate plane
                if (plane !in 0 until 4)
                    continue

                val tile = definition.tiles[plane][localX][localY]

                if(tile == null)
                {

                    val landscapeTile = LandscapeTile.Builder()
                        .coordinates(localX, localY)
                        .plane(plane)
                            .build()

                    landscapeTile.locs.add(LandscapeLoc(id, type, orientation))

                    definition.tiles[plane][localX][localY] = landscapeTile

                }
                else
                {

                    tile.locs.add(LandscapeLoc(id, type, orientation))

                }

            }
        }
    }

}