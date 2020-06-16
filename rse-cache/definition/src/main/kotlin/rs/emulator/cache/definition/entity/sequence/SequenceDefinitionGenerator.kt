package rs.emulator.cache.definition.entity.sequence

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.definition.generator.DefinitionGenerator
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.cache.store.index.archive.ArchiveConfig

/**
 *
 * @author Chk
 */
class SequenceDefinitionGenerator : DefinitionGenerator<SequenceDefinition>()
{

    override val definitionClass: Class<SequenceDefinition> = SequenceDefinition::class.java

    override val indexConfig: IndexConfig = IndexConfig.CONFIGS

    override val archive: Int = ArchiveConfig.VAR_BIT.identifier

    override fun generate(id: Int, reader: BufferedReader): SequenceDefinition = SequenceDefinition(id)

    override fun decode(definition: SequenceDefinition, opcode: Int, reader: BufferedReader)
    {

        var var10000: IntArray

        var var3: Int

        when (opcode)
        {

            1 ->
            {

                var3 = reader.getUnsigned(DataType.SHORT).toInt()

                definition.frameLengths = IntArray(var3)

                (0 until var3).forEach {
                    definition.frameLengths[it] = reader.getUnsigned(DataType.SHORT).toInt()
                }

                definition.frameIds = IntArray(var3)

                (0 until var3).forEach {
                    definition.frameIds[it] = reader.getUnsigned(DataType.SHORT).toInt()
                }

                (0 until var3).forEach {
                    var10000 = definition.frameIds
                    var10000[it] += (reader.getUnsigned(DataType.SHORT).toInt() shl 16)
                }

            }

            2 -> definition.frameStep = reader.getUnsigned(DataType.SHORT).toInt()

            3 ->
            {

                var3 = reader.getUnsigned(DataType.BYTE).toInt()

                definition.interLeaveLeave = IntArray(var3 + 1)

                (0 until var3).forEach {
                    definition.interLeaveLeave[it] = reader.getUnsigned(DataType.BYTE).toInt()
                }

                definition.interLeaveLeave[var3] = 9999999

            }

            4 -> definition.stretches = true

            5 -> definition.forcedPriority = reader.getUnsigned(DataType.BYTE).toInt()

            6 -> definition.leftHandItem = reader.getUnsigned(DataType.SHORT).toInt()

            7 -> definition.rightHandItem = reader.getUnsigned(DataType.SHORT).toInt()

            8 -> definition.maxLoops = reader.getUnsigned(DataType.BYTE).toInt()

            9 -> definition.precedenceAnimating = reader.getUnsigned(DataType.BYTE).toInt()

            10 -> definition.priority = reader.getUnsigned(DataType.BYTE).toInt()

            11 -> definition.replyMode = reader.getUnsigned(DataType.BYTE).toInt()

            12 ->
            {

                var3 = reader.getUnsigned(DataType.BYTE).toInt()

                definition.chatFrameIds = IntArray(var3)

                (0 until var3).forEach {
                    definition.chatFrameIds[it] = reader.getUnsigned(DataType.SHORT).toInt()
                }

                (0 until var3).forEach {
                    var10000 = definition.chatFrameIds
                    var10000[it] += (reader.getUnsigned(DataType.SHORT).toInt() shl 16)
                }

            }

            13 ->
            {

                var3 = reader.getUnsigned(DataType.BYTE).toInt()

                definition.frameSounds = IntArray(var3)

                (0 until var3).forEach {
                    definition.frameSounds[it] = reader.getUnsigned(DataType.TRI_BYTE).toInt()
                }

            }

        }

    }

}