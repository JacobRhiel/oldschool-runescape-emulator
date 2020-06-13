package rs.emulator.cache.definition.varp.player

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.definition.generator.DefinitionGenerator
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.cache.store.index.archive.ArchiveConfig

/**
 *
 * @author Chk
 */
class VarPlayerDefinitionGenerator : DefinitionGenerator<VarPlayerDefinition>()
{

    override val definitionClass: Class<VarPlayerDefinition> = VarPlayerDefinition::class.java

    override val indexConfig: IndexConfig = IndexConfig.CONFIGS

    override val archive: Int = ArchiveConfig.VAR_PLAYER.identifier

    override fun generate(id: Int, reader: BufferedReader): VarPlayerDefinition = VarPlayerDefinition(id)

    override fun decode(definition: VarPlayerDefinition, opcode: Int, reader: BufferedReader)
    {
        when(opcode)
        {
            5 -> definition.configType = reader.getUnsigned(DataType.SHORT).toInt()
        }
    }

}