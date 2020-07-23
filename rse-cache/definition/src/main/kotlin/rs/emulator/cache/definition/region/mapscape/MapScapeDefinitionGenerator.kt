package rs.emulator.cache.definition.region.mapscape

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.definition.generator.DefinitionGenerator
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.cache.store.index.archive.ArchiveConfig

/**
 *
 * @author Chk
 */
class MapScapeDefinitionGenerator : DefinitionGenerator<MapScapeDefinition>()
{

    override val definitionClass: Class<MapScapeDefinition> = MapScapeDefinition::class.java

    override val indexConfig: IndexConfig = IndexConfig.MAPS

    override val archive: Int = -1

    override val namedArchive: Boolean = true

    override fun generate(id: Int, reader: BufferedReader): MapScapeDefinition = MapScapeDefinition(id)

    override fun generateArchiveName(id: Int): String = "m${(id shr 8)}_${(id and 0xFF)}"

    override fun decodeHeader(id: Int, reader: BufferedReader): MapScapeDefinition
    {
        val definition = generate(id, reader)
        decode(definition, -1, reader)
        return definition
    }

    override fun decode(definition: MapScapeDefinition, opcode: Int, reader: BufferedReader)
    {

        val rx = ((definition.id shr 8) and 0xFF) shl 6

        val ry = (definition.id and 0xFF) shl 6

        for (plane in 0 until 4)
        {
            for (x in 0 until 64)
            {
                for (z in 0 until 64)
                {

                    val builder = MapScapeTile.Builder()
                        .coordinates(x, z).plane(plane)

                    while (true)
                    {
                        val attribute: Int = reader.getUnsigned(DataType.BYTE).toInt()
                        if (attribute == 0)
                            break
                        else if (attribute == 1)
                        {
                            builder.plane(reader.getUnsigned(DataType.BYTE).toInt())
                            break
                        } else if (attribute <= 49)
                            builder.attribute(attribute).overlay(reader.getSigned(DataType.BYTE).toByte())
                        else if (attribute <= 81)
                            builder.settings((attribute - 49).toByte())
                        else
                            builder.underlay((attribute - 81).toByte())
                    }

                    definition.tiles[plane][x][z] = builder.build()

                }
            }
        }
    }

}