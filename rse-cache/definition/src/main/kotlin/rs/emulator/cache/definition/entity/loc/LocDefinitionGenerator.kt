package rs.emulator.cache.definition.entity.loc

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.definition.generator.DefinitionGenerator
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.cache.store.index.archive.ArchiveConfig
import rs.emulator.definitions.entity.loc.LocDefinition

/**
 *
 * @author Chk
 */
class LocDefinitionGenerator : DefinitionGenerator<LocDefinition>()
{

    override val definitionClass: Class<LocDefinition> = LocDefinition::class.java

    override val indexConfig: IndexConfig = IndexConfig.CONFIGS

    override val archive: Int = ArchiveConfig.LOC.identifier

    override fun generate(id: Int, reader: BufferedReader): LocDefinition = LocDefinition(id)

    override fun decodeHeader(id: Int, reader: BufferedReader): LocDefinition {
        val d = super.decodeHeader(id, reader)
        if (d.ignoreClipOnAlternativeRoute) {
            d.solidType = 0
            d.projectileClipped = false
        }
        return d
    }

    override fun decode(definition: LocDefinition, opcode: Int, reader: BufferedReader)
    {
        when (opcode) {
            1 -> {
                val count = reader.getUnsigned(DataType.BYTE).toInt()
                for (i in 0 until count) {
                    reader.getUnsigned(DataType.SHORT).toInt() // Model
                    reader.getUnsigned(DataType.BYTE).toInt() // Model type
                }
            }
            2 -> definition.name = reader.string
            5 -> {
                val count = reader.getUnsigned(DataType.BYTE).toInt()
                for (i in 0 until count) {
                    reader.getUnsigned(DataType.SHORT).toInt() // Model
                }
            }
            14 -> definition.width = reader.getUnsigned(DataType.BYTE).toInt().toInt()
            15 -> definition.length = reader.getUnsigned(DataType.BYTE).toInt().toInt()
            17 -> {
                definition.solidType = 0
                definition.projectileClipped = false
            }
            18 -> definition.projectileClipped = false
            19 -> definition.isWallOrDoor = reader.getUnsigned(DataType.BYTE).toInt().toInt() == 1
            24 -> {
                definition.animation = reader.getUnsigned(DataType.SHORT).toInt()
                if (definition.animation == 65535) {
                    definition.animation = -1
                }
            }
            27 -> {
                definition.solidType = 1
            }
            28 -> reader.getUnsigned(DataType.BYTE).toInt()
            29 -> reader.getSigned(DataType.BYTE).toInt()
            in 30 until 35 -> {
                definition.options[opcode - 30] = reader.string
                if (definition.options[opcode - 30]?.toLowerCase() == "null") {
                    definition.options[opcode - 30] = null
                }
            }
            39 -> reader.getSigned(DataType.BYTE).toInt()
            40 -> {
                val count = reader.getUnsigned(DataType.BYTE).toInt()
                for (i in 0 until count) {
                    reader.getUnsigned(DataType.SHORT).toInt() // Recolor src
                    reader.getUnsigned(DataType.SHORT).toInt() // Recolor dst
                }
            }
            41 -> {
                val count = reader.getUnsigned(DataType.BYTE).toInt()
                for (i in 0 until count) {
                    reader.getUnsigned(DataType.SHORT).toInt() // Retexture src
                    reader.getUnsigned(DataType.SHORT).toInt() // Retexture dst
                }
            }
            60 -> reader.getUnsigned(DataType.SHORT).toInt()
            62 -> definition.rotated = true
            65 -> reader.getUnsigned(DataType.SHORT).toInt()
            66 -> reader.getUnsigned(DataType.SHORT).toInt()
            67 -> reader.getUnsigned(DataType.SHORT).toInt()
            68 -> reader.getUnsigned(DataType.SHORT).toInt()
            69 -> definition.clipMask = reader.getUnsigned(DataType.BYTE).toInt()
            70 -> reader.getSigned(DataType.SHORT).toInt()
            71 -> reader.getSigned(DataType.SHORT).toInt()
            72 -> reader.getSigned(DataType.SHORT).toInt()
            73 -> definition.obstructive = true
            74 -> {
                definition.ignoreClipOnAlternativeRoute = true
            }
            75 -> reader.getUnsigned(DataType.BYTE).toInt()
            77, 92 -> {
                definition.varbit = reader.getUnsigned(DataType.SHORT).toInt()
                definition.varp = reader.getUnsigned(DataType.SHORT).toInt()

                if (definition.varbit == 65535) {
                    definition.varbit = -1
                }
                if (definition.varp == 65535) {
                    definition.varp = -1
                }

                if (opcode == 92) {
                    reader.getUnsigned(DataType.SHORT).toInt()
                }

                val count = reader.getUnsigned(DataType.BYTE).toInt().toInt()

                definition.transforms = Array(count + 1) { 0 }
                for (i in 0..count) {
                    val transform = reader.getUnsigned(DataType.SHORT).toInt()
                    definition.transforms!![i] = transform
                }
            }
            78 -> {
                reader.getUnsigned(DataType.SHORT).toInt()
                reader.getUnsigned(DataType.BYTE).toInt()
            }
            79 -> {
                reader.getUnsigned(DataType.SHORT).toInt()
                reader.getUnsigned(DataType.SHORT).toInt()
                reader.getUnsigned(DataType.BYTE).toInt()
                val count = reader.getUnsigned(DataType.BYTE).toInt().toInt()
                for (i in 0 until count) {
                    reader.getUnsigned(DataType.SHORT).toInt()
                }
            }
            81 -> reader.getUnsigned(DataType.BYTE).toInt()
            82 -> reader.getUnsigned(DataType.SHORT).toInt()
            249 -> readParams(reader)
        }
    }

}