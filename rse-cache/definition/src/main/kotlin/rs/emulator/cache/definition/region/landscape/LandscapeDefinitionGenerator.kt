package rs.emulator.cache.definition.region.landscape

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.definition.generator.DefinitionGenerator
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.cache.store.index.archive.ArchiveConfig

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

    override fun generateArchiveName(id: Int): String = "l${id}_${id}"

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
                val plane = position shr 12 and 0x3
                val attributes: Int = reader.getUnsigned(DataType.BYTE).toInt()
                val type = attributes shr 2
                val orientation = attributes and 0x3
                definition.tiles.add(
                    LandscapeTile(
                        id,
                        type,
                        orientation,
                        localX,
                        localY,
                        plane
                    )
                )
            }
        }
    }

}