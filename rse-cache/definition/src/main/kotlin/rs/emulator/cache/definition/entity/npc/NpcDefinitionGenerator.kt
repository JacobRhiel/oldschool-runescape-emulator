package rs.emulator.cache.definition.entity.npc

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.definition.generator.DefinitionGenerator
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.cache.store.index.archive.ArchiveConfig
import rs.emulator.definitions.impl.entity.npc.NpcDefinition

/**
 *
 * @author Chk
 */
class NpcDefinitionGenerator : DefinitionGenerator<NpcDefinition>()
{

    override val definitionClass: Class<NpcDefinition> = NpcDefinition::class.java

    override val indexConfig: IndexConfig = IndexConfig.CONFIGS

    override val archive: Int = ArchiveConfig.NPC.identifier

    override fun generate(id: Int, reader: BufferedReader): NpcDefinition = NpcDefinition(id)

    override fun decode(definition: NpcDefinition, opcode: Int, reader: BufferedReader)
    {

        when (opcode) {
            1 -> {
                val count = reader.getUnsigned(DataType.BYTE).toInt()
                for (i in 0 until count) {
                    reader.getUnsigned(DataType.SHORT).toInt()
                }
            }
            2 -> definition.name = reader.string
            12 -> definition.size = reader.getUnsigned(DataType.BYTE).toInt()
            13 -> definition.standAnim = reader.getUnsigned(DataType.SHORT).toInt()
            14 -> definition.walkAnim = reader.getUnsigned(DataType.SHORT).toInt()
            15 -> definition.render3 = reader.getUnsigned(DataType.SHORT).toInt()
            16 -> definition.render4 = reader.getUnsigned(DataType.SHORT).toInt()
            17 -> {
                definition.walkAnim = reader.getUnsigned(DataType.SHORT).toInt()
                definition.render5 = reader.getUnsigned(DataType.SHORT).toInt()
                definition.render6 = reader.getUnsigned(DataType.SHORT).toInt()
                definition.render7 = reader.getUnsigned(DataType.SHORT).toInt()
            }
            in 30 until 35 -> {
                definition.options[opcode - 30] = reader.string
                if (definition.options[opcode - 30]?.toLowerCase() == "null") {
                    definition.options[opcode - 30] = null
                }
            }
            40 -> {
                val count = reader.getUnsigned(DataType.BYTE).toInt()
                for (i in 0 until count) {
                    reader.getUnsigned(DataType.SHORT).toInt()
                    reader.getUnsigned(DataType.SHORT).toInt()
                }
            }
            41 -> {
                val count = reader.getUnsigned(DataType.BYTE).toInt()
                for (i in 0 until count) {
                    reader.getUnsigned(DataType.SHORT).toInt()
                    reader.getUnsigned(DataType.SHORT).toInt()
                }
            }
            60 -> {
                val count = reader.getUnsigned(DataType.BYTE).toInt()
                for (i in 0 until count) {
                    reader.getUnsigned(DataType.SHORT).toInt()
                }
            }
            93 -> definition.visibleMapDot = false
            95 -> definition.combatLevel = reader.getUnsigned(DataType.SHORT).toInt()
            97 -> definition.width = reader.getUnsigned(DataType.SHORT).toInt()
            98 -> definition.headIcon = reader.getUnsigned(DataType.SHORT).toInt()
            99 -> definition.render = true
            100 -> reader.getSigned(DataType.BYTE).toInt()
            101 -> reader.getSigned(DataType.BYTE).toInt()
            102 -> definition.headIcon = reader.getUnsigned(DataType.SHORT).toInt()
            103 -> reader.getUnsigned(DataType.SHORT).toInt()
            106, 118 -> {
                definition.varbit = reader.getUnsigned(DataType.SHORT).toInt()
                definition.varp = reader.getUnsigned(DataType.SHORT).toInt()

                if (definition.varbit == 65535) {
                    definition.varbit = -1
                }
                if (definition.varp == 65535) {
                    definition.varp = -1
                }

                if (opcode == 118) {
                    reader.getUnsigned(DataType.SHORT).toInt()
                }

                val count = reader.getUnsigned(DataType.BYTE).toInt()

                definition.transforms = Array(count.toInt() + 1) { 0 }
                for (i in 0..count) {
                    val transform = reader.getUnsigned(DataType.SHORT).toInt()
                    definition.transforms!![i] = transform
                }
            }
            107 -> definition.interactable = false
            111 -> definition.pet = true
            112 -> reader.getUnsigned(DataType.BYTE).toInt()
            249 -> readParams(reader)
        }

    }

}