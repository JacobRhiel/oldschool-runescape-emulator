package rs.emulator.cache.definition.widget.struct

import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.definition.generator.DefinitionGenerator
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.cache.store.index.archive.ArchiveConfig

/**
 *
 * @author Chk
 */
class StructDefinitionGenerator : DefinitionGenerator<StructDefinition>()
{

    override val definitionClass: Class<StructDefinition> = StructDefinition::class.java

    override val indexConfig: IndexConfig = IndexConfig.CONFIGS

    override val archive: Int = ArchiveConfig.STRUCT.identifier

    override fun generate(id: Int, reader: BufferedReader): StructDefinition = StructDefinition(id)

    override fun decode(definition: StructDefinition, opcode: Int, reader: BufferedReader)
    {
        when(opcode)
        {
            249 -> definition.params = readParams(reader)
        }
    }

}