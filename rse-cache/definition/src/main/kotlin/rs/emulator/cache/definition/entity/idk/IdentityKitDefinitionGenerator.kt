package rs.emulator.cache.definition.entity.idk

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.definition.generator.DefinitionGenerator
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.cache.store.index.archive.ArchiveConfig

/**
 *
 * @author Chk
 */
class IdentityKitDefinitionGenerator : DefinitionGenerator<IdentityKitDefinition>()
{

    override val definitionClass: Class<IdentityKitDefinition> = IdentityKitDefinition::class.java

    override val indexConfig: IndexConfig = IndexConfig.CONFIGS

    override val archive: Int = ArchiveConfig.IDENTITY_KIT.identifier

    override fun generate(id: Int, reader: BufferedReader): IdentityKitDefinition = IdentityKitDefinition(id)

    override fun decode(definition: IdentityKitDefinition, opcode: Int, reader: BufferedReader)
    {
        when(opcode)
        {
            1 ->
            {
                definition.bodyPartId = reader.getUnsigned(DataType.BYTE).toInt()
            }
            2 ->
            {
                val length: Int = reader.getUnsigned(DataType.BYTE).toInt()
                definition.models = IntArray(length)
                for (index in 0 until length)
                {
                    definition.models[index] = reader.getUnsigned(DataType.SHORT).toInt()
                }
            }
            3 ->
            {
                definition.nonSelectable = true
            }
            40 ->
            {
                val length: Int = reader.getUnsigned(DataType.BYTE).toInt()
                definition.recolorToFind = ShortArray(length)
                definition.recolorToReplace = ShortArray(length)
                for (index in 0 until length)
                {
                    definition.recolorToFind[index] = reader.getSigned(DataType.SHORT).toShort()
                    definition.recolorToReplace[index] = reader.getSigned(DataType.SHORT).toShort()
                }
            }
            41 ->
            {
                val length: Int = reader.getUnsigned(DataType.BYTE).toInt()
                definition.retextureToFind = ShortArray(length)
                definition.retextureToReplace = ShortArray(length)
                for (index in 0 until length)
                {
                    definition.retextureToFind[index] = reader.getSigned(DataType.SHORT).toShort()
                    definition.retextureToReplace[index] = reader.getSigned(DataType.SHORT).toShort()
                }
            }
            in 60 until 70 ->
            {
                definition.chatheadModels[opcode - 60] = reader.getUnsigned(DataType.SHORT).toInt()
            }
        }
    }


}