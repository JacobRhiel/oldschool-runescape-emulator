package rs.emulator.cache.definition.widget.enum

import it.unimi.dsi.fastutil.ints.Int2ReferenceArrayMap
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.definition.generator.DefinitionGenerator
import rs.emulator.definitions.scripts.ScriptVarType
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.cache.store.index.archive.ArchiveConfig
import rs.emulator.definitions.enums.EnumDefinition

/**
 *
 * @author Chk
 */
class EnumDefinitionGenerator : DefinitionGenerator<EnumDefinition>() {

    override val definitionClass: Class<EnumDefinition> = EnumDefinition::class.java

    override val indexConfig: IndexConfig = IndexConfig.CONFIGS

    override val archive: Int = ArchiveConfig.ENUM.identifier

    override fun generate(id: Int, reader: BufferedReader): EnumDefinition =
        EnumDefinition(id)

    override fun decodeHeader(id: Int, reader: BufferedReader): EnumDefinition {

        if (reader.toArray().size == 1 && reader.toArray()[0].toInt() == 0)
            return EnumDefinition(id)

        return super.decodeHeader(id, reader)
    }

    override fun decode(definition: EnumDefinition, opcode: Int, reader: BufferedReader) {

        var size: Int

        var keys: IntArray

        var index: Int

        when (opcode) {

            1 -> definition.keyType = ScriptVarType.forCharKey(reader.getUnsigned(DataType.BYTE).toChar())

            2 -> definition.valType = ScriptVarType.forCharKey(reader.getUnsigned(DataType.BYTE).toChar())

            3 -> definition.defaultString = reader.string

            4 -> definition.defaultInt = reader.getUnsigned(DataType.INT).toInt()

            5 -> {

                size = reader.getUnsigned(DataType.SHORT).toInt()

                keys = IntArray(size)

                val stringValues = Int2ReferenceArrayMap<String>()

                (0 until size).forEach { index ->

                    keys[index] = reader.getSigned(DataType.INT).toInt()

                    stringValues[index] = reader.string

                }

                definition.size = size

                definition.keys = keys

                definition.stringValues = stringValues

            }

            6 -> {

                size = reader.getUnsigned(DataType.SHORT).toInt()

                keys = IntArray(size)

                val intValues = IntArray(size)

                (0 until size).forEach { index ->

                    keys[index] = reader.getSigned(DataType.INT).toInt()

                    intValues[index] = reader.getSigned(DataType.INT).toInt()

                }

                definition.size = size

                definition.keys = keys

                definition.intValues = intValues

            }

        }

    }

}