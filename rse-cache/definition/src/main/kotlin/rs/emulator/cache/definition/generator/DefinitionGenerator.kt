package rs.emulator.cache.definition.generator

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.definitions.Definition
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.utilities.logger.info

/**
 *
 * @author Chk
 */
abstract class DefinitionGenerator<T : Definition>
{

    abstract val definitionClass: Class<out T>

    abstract val indexConfig: IndexConfig

    abstract val archive: Int

    open val namedArchive: Boolean = false

    open val shiftedId: Int = -1

    abstract fun generate(id: Int, reader: BufferedReader) : T


    open fun decode(definition: T, opcode: Int, reader: BufferedReader)
    {

    }

    open fun decodeHeader(id: Int, reader: BufferedReader): T
    {

        val definition = generate(id, reader)

        if(reader.readableBytes <= 0)
        {
            info("No readable bytes to decode definition for {}.", definition.id)
        }
        else
        {
            while (true)
            {

                val opcode: Int = reader.getUnsigned(DataType.BYTE).toInt()

                println("opcode: $opcode")

                if (opcode == 0)
                    break

                decode(definition, opcode, reader)

            }
        }
        return definition
    }

    open fun generateArchiveName(id: Int) : String = id.toString()

    fun readParams(reader: BufferedReader) : HashMap<Int, Any>
    {

        val map = hashMapOf<Int, Any>()

        val length = reader.getUnsigned(DataType.BYTE).toInt()
        for (i in 0 until length) {
            val isString = reader.getUnsigned(DataType.BYTE).toInt() == 1
            val id = reader.getUnsigned(DataType.TRI_BYTE).toInt()
            if (isString) {
                map[id] = reader.string
            } else {
                map[id] = reader.getSigned(DataType.INT).toInt()
            }
        }
        return map
    }

}