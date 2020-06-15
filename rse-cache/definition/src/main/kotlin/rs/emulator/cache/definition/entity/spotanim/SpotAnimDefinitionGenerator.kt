package rs.emulator.cache.definition.entity.spotanim

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.definition.generator.DefinitionGenerator
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.cache.store.index.archive.ArchiveConfig

/**
 *
 * @author Chk
 */
class SpotAnimDefinitionGenerator : DefinitionGenerator<SpotAnimDefinition>()
{

    override val definitionClass: Class<SpotAnimDefinition> = SpotAnimDefinition::class.java

    override val indexConfig: IndexConfig = IndexConfig.CONFIGS

    override val archive: Int = ArchiveConfig.VAR_BIT.identifier

    override fun generate(id: Int, reader: BufferedReader): SpotAnimDefinition = SpotAnimDefinition(id)

    override fun decode(definition: SpotAnimDefinition, opcode: Int, reader: BufferedReader)
    {

        when (opcode)
        {

            1 -> definition.modelId = reader.getUnsigned(DataType.SHORT).toInt()

            2 -> definition.animationId = reader.getUnsigned(DataType.SHORT).toInt()

            4 -> definition.resizeX = reader.getUnsigned(DataType.SHORT).toInt()

            5 -> definition.resizeY = reader.getUnsigned(DataType.SHORT).toInt()

            6 -> definition.rotation = reader.getUnsigned(DataType.SHORT).toInt()

            7 -> definition.ambient = reader.getUnsigned(DataType.BYTE).toInt()

            8 -> definition.contrast = reader.getUnsigned(DataType.BYTE).toInt()

            40 ->
            {

                val length = reader.getUnsigned(DataType.BYTE).toInt()

                definition.recolorToFind = ShortArray(length)

                definition.recolorToReplace = ShortArray(length)

                (0 until length).forEach {
                    definition.recolorToFind[it] = reader.getUnsigned(DataType.SHORT).toShort()
                    definition.recolorToReplace[it] = reader.getUnsigned(DataType.SHORT).toShort()
                }

            }

            41 ->
            {

                val length = reader.getUnsigned(DataType.BYTE).toInt()

                definition.textureToFind = ShortArray(length)

                definition.textureToReplace = ShortArray(length)

                (0 until length).forEach {
                    definition.textureToFind[it] = reader.getUnsigned(DataType.SHORT).toShort()
                    definition.textureToReplace[it] = reader.getUnsigned(DataType.SHORT).toShort()
                }

            }

        }

    }

}