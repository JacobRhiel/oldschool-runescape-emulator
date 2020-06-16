package rs.emulator.cache.definition.widget.script

import it.unimi.dsi.fastutil.ints.Int2ReferenceArrayMap
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.definition.generator.DefinitionGenerator
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.cache.store.index.archive.ArchiveConfig

/**
 *
 * @author Chk
 */
class ScriptDefinitionGenerator : DefinitionGenerator<ScriptDefinition>()
{

    override val definitionClass: Class<ScriptDefinition> = ScriptDefinition::class.java

    override val indexConfig: IndexConfig = IndexConfig.CONFIGS

    override val archive: Int = ArchiveConfig.STRUCT.identifier

    override fun generate(id: Int, reader: BufferedReader): ScriptDefinition = ScriptDefinition(id)

    override fun decodeHeader(id: Int, reader: BufferedReader): ScriptDefinition
    {
        val definition = generate(id, reader)
        decode(definition, -1, reader)
        return definition
    }
    
    override fun decode(definition: ScriptDefinition, opcode: Int, reader: BufferedReader)
    {
        reader.markReaderIndex(reader.readableBytes - 2)
        val switchLength: Int = reader.getUnsigned(DataType.SHORT).toInt()
        val endIdx: Int = reader.readableBytes - 2 - switchLength - 12
        reader.markReaderIndex(endIdx)
        val numOpcodes: Int = reader.getSigned(DataType.INT).toInt()
        val localIntCount: Int = reader.getUnsigned(DataType.SHORT).toInt()
        val localStringCount: Int = reader.getUnsigned(DataType.SHORT).toInt()
        val intStackCount: Int = reader.getUnsigned(DataType.SHORT).toInt()
        val stringStackCount: Int = reader.getUnsigned(DataType.SHORT).toInt()
        val numSwitches: Int = reader.getUnsigned(DataType.BYTE).toInt()
        var opcode: Int
        var i: Int
        if (numSwitches > 0)
        {
            val switches = Int2ReferenceArrayMap<HashMap<Int, Int>>(numSwitches)

            var i = 0
            while (i < numSwitches)
            {
                switches[i] = hashMapOf()
                var var15: Int = reader.getUnsigned(DataType.SHORT).toInt()
                while (var15-- > 0)
                {
                    opcode = reader.getSigned(DataType.INT).toInt()
                    i = reader.getSigned(DataType.INT).toInt()
                    switches[i][opcode] = i
                }
                ++i
            }

            definition.switches = switches
        }

        definition.localIntCount = localIntCount
        definition.localStringCount = localStringCount
        definition.intStackCount = intStackCount
        definition.stringStackCount = stringStackCount
        reader.resetReaderIndex()
        reader.string
        val instructions = IntArray(numOpcodes)
        val intOperands = IntArray(numOpcodes)
        val stringOperands = arrayOfNulls<String>(numOpcodes)
        definition.instructions = instructions
        definition.intOperands = intOperands
        definition.stringOperands = stringOperands

        i = 0
        while (reader.readerIndex() < endIdx)
        {

            opcode = reader.getUnsigned(DataType.SHORT).toInt()

            if (opcode == 3)
                stringOperands[i] = reader.string
            else if (opcode < 100 && opcode != 21 && opcode != 38 && opcode != 39)
                intOperands[i] = reader.getSigned(DataType.INT).toInt()
            else
                intOperands[i] = reader.getUnsigned(DataType.BYTE).toInt()

            instructions[i++] = opcode
        }
    }

}