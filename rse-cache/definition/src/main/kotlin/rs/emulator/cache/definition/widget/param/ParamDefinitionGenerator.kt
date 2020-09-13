package rs.emulator.cache.definition.widget.param

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.definition.generator.DefinitionGenerator
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.cache.store.index.archive.ArchiveConfig
import rs.emulator.definitions.impl.scripts.ScriptVarType

/**
 *
 * @author Chk
 */
class ParamDefinitionGenerator : DefinitionGenerator<ParamDefinition>()
{

    override val definitionClass: Class<ParamDefinition> = ParamDefinition::class.java

    override val indexConfig: IndexConfig = IndexConfig.CONFIGS

    override val archive: Int = ArchiveConfig.PARAMS.identifier

    override fun generate(id: Int, reader: BufferedReader): ParamDefinition = ParamDefinition(id)

    override fun decode(definition: ParamDefinition, opcode: Int, reader: BufferedReader)
    {
        when(opcode)
        {
            1 -> definition.type = ScriptVarType.forCharKey(reader.getSigned(DataType.BYTE).toChar())
            2 -> definition.defaultInt = reader.getSigned(DataType.INT).toInt()
            4 -> definition.isMembers = false
            5 -> definition.defaultString = reader.string
        }
    }

}