package rs.emulator.cache.definition.entity.obj

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.definition.generator.DefinitionGenerator
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.cache.store.index.archive.ArchiveConfig
import rs.emulator.definitions.entity.obj.ObjDefinition

/**
 *
 * @author Chk
 */
class ObjDefinitionGenerator : DefinitionGenerator<ObjDefinition>()
{

    override val definitionClass: Class<ObjDefinition> = ObjDefinition::class.java

    override val indexConfig: IndexConfig = IndexConfig.CONFIGS

    override val archive: Int = ArchiveConfig.OBJ.identifier

    override fun generate(id: Int, reader: BufferedReader): ObjDefinition = ObjDefinition(id)

    override fun decode(definition: ObjDefinition, opcode: Int, reader: BufferedReader)
    {


        when (opcode)
        {
            1                -> reader.getUnsigned(DataType.SHORT).toInt()
            2                -> definition.name = reader.string
            4                -> reader.getUnsigned(DataType.SHORT).toInt()
            5                -> reader.getUnsigned(DataType.SHORT).toInt()
            6                -> reader.getUnsigned(DataType.SHORT).toInt()
            7                -> reader.getUnsigned(DataType.SHORT).toInt()
            8                -> reader.getUnsigned(DataType.SHORT).toInt()
            11               -> definition.stacks = true
            12               -> definition.cost = reader.getSigned(DataType.INT).toInt()
            16               -> definition.members = true
            23               ->
            {
                reader.getUnsigned(DataType.SHORT).toInt()
                reader.getUnsigned(DataType.BYTE).toInt()
            }
            24               -> reader.getUnsigned(DataType.SHORT).toInt()
            25               ->
            {
                reader.getUnsigned(DataType.SHORT).toInt()
                reader.getUnsigned(DataType.BYTE).toInt()
            }
            26               -> reader.getUnsigned(DataType.SHORT).toInt()
            in 30 until 35   ->
            {
                definition.groundMenu[opcode - 30] = reader.string
                if (definition.groundMenu[opcode - 30]!!.toLowerCase() == "null")
                {
                    definition.groundMenu[opcode - 30] = null
                }
            }
            in 35 until 40   -> definition.inventoryMenu[opcode - 35] = reader.string
            40               ->
            {
                val count = reader.getUnsigned(DataType.BYTE).toInt()

                for (i in 0 until count)
                {
                    reader.getUnsigned(DataType.SHORT).toInt()
                    reader.getUnsigned(DataType.SHORT).toInt()
                }
            }
            41               ->
            {
                val count = reader.getUnsigned(DataType.BYTE).toInt()

                for (i in 0 until count)
                {
                    reader.getUnsigned(DataType.SHORT).toInt()
                    reader.getUnsigned(DataType.SHORT).toInt()
                }
            }
            42               -> reader.getSigned(DataType.BYTE).toInt()
            65               -> definition.grandExchange = true
            78               -> reader.getUnsigned(DataType.SHORT).toInt()
            79               -> reader.getUnsigned(DataType.SHORT).toInt()
            90               -> reader.getUnsigned(DataType.SHORT).toInt()
            91               -> reader.getUnsigned(DataType.SHORT).toInt()
            92               -> reader.getUnsigned(DataType.SHORT).toInt()
            93               -> reader.getUnsigned(DataType.SHORT).toInt()
            95               -> reader.getUnsigned(DataType.SHORT).toInt()
            97               -> definition.noteLinkId = reader.getUnsigned(DataType.SHORT).toInt()
            98               -> definition.noteTemplateId = reader.getUnsigned(DataType.SHORT).toInt()
            in 100 until 110 ->
            {
                reader.getUnsigned(DataType.SHORT).toInt()
                reader.getUnsigned(DataType.SHORT).toInt()
            }
            110              -> reader.getUnsigned(DataType.SHORT).toInt()
            111              -> reader.getUnsigned(DataType.SHORT).toInt()
            112              -> reader.getUnsigned(DataType.SHORT).toInt()
            113              -> reader.getSigned(DataType.BYTE).toInt()
            114              -> reader.getSigned(DataType.BYTE).toInt()
            115              -> definition.teamCape = reader.getUnsigned(DataType.BYTE).toInt().toInt()
            139              -> reader.getUnsigned(DataType.SHORT).toInt()
            140              -> reader.getUnsigned(DataType.SHORT).toInt()
            148              -> definition.placeholderLink = reader.getUnsigned(DataType.SHORT).toInt()
            149              -> definition.placeholderTemplate = reader.getUnsigned(DataType.SHORT).toInt()
            249              ->
            {
                definition.params.putAll(readParams(reader))

                for (i in 0 until 8)
                {
                    val paramId = 451 + i
                    val option = definition.params.get(paramId) as? String ?: continue
                    definition.equipmentMenu[i] = option
                }
            }
        }
    }

}