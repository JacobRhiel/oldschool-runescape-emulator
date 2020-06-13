package rs.emulator.cache.definition.widget.inv

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.cache.store.index.archive.ArchiveConfig
import rs.emulator.cache.definition.generator.DefinitionGenerator

/**
 *
 * @author Chk
 */
class InvDefinitionGenerator : DefinitionGenerator<InvDefinition>()
{

    override val definitionClass: Class<InvDefinition> = InvDefinition::class.java

    override val indexConfig: IndexConfig = IndexConfig.CONFIGS

    override val archive: Int = ArchiveConfig.INV.identifier

    override fun generate(id: Int, reader: BufferedReader): InvDefinition = InvDefinition(id)

    override fun decode(definition: InvDefinition, opcode: Int, reader: BufferedReader)
    {
        when (opcode)
        {
            2 -> definition.size = reader.getUnsigned(DataType.SHORT).toInt()
        }
    }

}