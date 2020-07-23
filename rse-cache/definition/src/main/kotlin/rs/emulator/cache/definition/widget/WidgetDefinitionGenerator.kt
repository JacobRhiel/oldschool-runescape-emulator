package rs.emulator.cache.definition.widget

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.definition.generator.DefinitionGenerator
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.definitions.scripts.ClientScript1Instruction
import rs.emulator.definitions.widget.WidgetDefinition
import java.util.*

/**
 *
 * @author Chk
 */
class WidgetDefinitionGenerator : DefinitionGenerator<WidgetDefinition>()
{

    override val definitionClass: Class<WidgetDefinition> = WidgetDefinition::class.java

    override val indexConfig: IndexConfig = IndexConfig.INTERFACES

    override val archive: Int = -1

    override fun generate(id: Int, reader: BufferedReader): WidgetDefinition = WidgetDefinition(id)

    override fun decodeHeader(id: Int, reader: BufferedReader) : WidgetDefinition
    {
        val definition = generate(id, reader)
        if (reader.toArray()[0].toInt() == -1)
            decodeIf3(definition, reader)
        else
            decodeIf1(definition, reader)
        return definition
    }

    private fun decodeIf1(definition: WidgetDefinition, var1: BufferedReader)
    {
        definition.isIf3 = false
        definition.type = var1.getUnsigned(DataType.BYTE).toInt()
        definition.menuType = var1.getUnsigned(DataType.BYTE).toInt()
        definition.contentType = var1.getUnsigned(DataType.SHORT).toInt()
        definition.originalX = var1.getSigned(DataType.SHORT).toInt()
        definition.originalY = var1.getSigned(DataType.SHORT).toInt()
        definition.originalWidth = var1.getUnsigned(DataType.SHORT).toInt()
        definition.originalHeight = var1.getUnsigned(DataType.SHORT).toInt()
        definition.opacity = var1.getUnsigned(DataType.BYTE).toInt()
        definition.parentId = var1.getUnsigned(DataType.SHORT).toInt()
        if (definition.parentId == 0xFFFF)
        {
            definition.parentId = -1
        } else
        {
            definition.parentId += definition.componentId and 0xFFFF.inv()
        }
        definition.hoveredSiblingId = var1.getUnsigned(DataType.SHORT).toInt()
        if (definition.hoveredSiblingId == 0xFFFF)
        {
            definition.hoveredSiblingId = -1
        }
        val var2 = var1.getUnsigned(DataType.BYTE).toInt()
        var var3: Int
        if (var2 > 0)
        {
            definition.alternateOperators = IntArray(var2)
            definition.alternateRhs = IntArray(var2)
            var3 = 0
            while (var3 < var2)
            {
                definition.alternateOperators[var3] = var1.getUnsigned(DataType.BYTE).toInt()
                definition.alternateRhs[var3] = var1.getUnsigned(DataType.SHORT).toInt()
                ++var3
            }
        }
        var3 = var1.getUnsigned(DataType.BYTE).toInt()
        var var4: Int
        var var5: Int
        var var6: Int
        if (var3 > 0)
        {
            definition.clientScripts = arrayOfNulls(var3)
            var4 = 0
            while (var4 < var3)
            {
                var5 = var1.getUnsigned(DataType.SHORT).toInt()
                val bytecode = IntArray(var5)
                var6 = 0
                while (var6 < var5)
                {
                    bytecode[var6] = var1.getUnsigned(DataType.SHORT).toInt()
                    if (bytecode[var6] == 0xFFFF)
                    {
                        bytecode[var6] = -1
                    }
                    val instructions: MutableList<ClientScript1Instruction> =
                        ArrayList()
                    var i = 0
                    while (i < bytecode.size)
                    {
                        val ins = ClientScript1Instruction()
                        ins.opcode = ClientScript1Instruction.Opcode.values()[bytecode[i++]]
                        val ac = ins.opcode.argumentCount
                        ins.operands = bytecode.copyOfRange(i, i + ac)
                        instructions.add(ins)
                        i += ac
                    }
                    definition.clientScripts[var4] = instructions.toTypedArray()
                    ++var6
                }
                ++var4
            }
        }
        if (definition.type == 0)
        {
            definition.scrollHeight = var1.getUnsigned(DataType.SHORT).toInt()
            definition.isHidden = var1.getUnsigned(DataType.BYTE).toInt() == 1
        }
        if (definition.type == 1)
        {
            var1.getUnsigned(DataType.SHORT).toInt()
            var1.getUnsigned(DataType.BYTE).toInt()
        }
        if (definition.type == 2)
        {
            definition.itemIds = IntArray(definition.originalWidth * definition.originalHeight)
            definition.itemQuantities = IntArray(definition.originalHeight * definition.originalWidth)
            var4 = var1.getUnsigned(DataType.BYTE).toInt()
            if (var4 == 1)
            {
                definition.clickMask = definition.clickMask or 268435456
            }
            var5 = var1.getUnsigned(DataType.BYTE).toInt()
            if (var5 == 1)
            {
                definition.clickMask = definition.clickMask or 1073741824
            }
            var6 = var1.getUnsigned(DataType.BYTE).toInt()
            if (var6 == 1)
            {
                definition.clickMask = definition.clickMask or Int.MIN_VALUE
            }
            val var7 = var1.getUnsigned(DataType.BYTE).toInt()
            if (var7 == 1)
            {
                definition.clickMask = definition.clickMask or 536870912
            }
            definition.xPitch = var1.getUnsigned(DataType.BYTE).toInt()
            definition.yPitch = var1.getUnsigned(DataType.BYTE).toInt()
            definition.xOffsets = IntArray(20)
            definition.yOffsets = IntArray(20)
            definition.sprites = IntArray(20)
            var var8: Int
            var8 = 0
            while (var8 < 20)
            {
                val var9 = var1.getUnsigned(DataType.BYTE).toInt()
                if (var9 == 1) {
                    definition.xOffsets[var8] = var1.getSigned(DataType.SHORT).toInt()
                    definition.yOffsets[var8] = var1.getSigned(DataType.SHORT).toInt()
                    definition.sprites[var8] = var1.getSigned(DataType.INT).toInt()
                } else
                {
                    definition.sprites[var8] = -1
                }
                ++var8
            }
            definition.configActions = arrayOfNulls(5)
            var8 = 0
            while (var8 < 5)
            {
                val var11 = var1.string
                if (var11.length > 0)
                {
                    definition.configActions[var8] = var11
                    definition.clickMask = definition.clickMask or (1 shl var8 + 23)
                }
                ++var8
            }
        }
        if (definition.type == 3)
        {
            definition.filled = var1.getUnsigned(DataType.BYTE).toInt() == 1
        }
        if (definition.type == 4 || definition.type == 1)
        {
            definition.xTextAlignment = var1.getUnsigned(DataType.BYTE).toInt()
            definition.yTextAlignment = var1.getUnsigned(DataType.BYTE).toInt()
            definition.lineHeight = var1.getUnsigned(DataType.BYTE).toInt()
            definition.fontId = var1.getUnsigned(DataType.SHORT).toInt()
            if (definition.fontId == 0xFFFF)
            {
                definition.fontId = -1
            }
            definition.textShadowed = var1.getUnsigned(DataType.BYTE).toInt() == 1
        }
        if (definition.type == 4)
        {
            definition.text = var1.string
            definition.alternateText = var1.string
        }
        if (definition.type == 1 || definition.type == 3 || definition.type == 4)
        {
            definition.textColor = var1.getSigned(DataType.INT).toInt()
        }
        if (definition.type == 3 || definition.type == 4)
        {
            definition.alternateTextColor = var1.getSigned(DataType.INT).toInt()
            definition.hoveredTextColor = var1.getSigned(DataType.INT).toInt()
            definition.alternateHoveredTextColor = var1.getSigned(DataType.INT).toInt()
        }
        if (definition.type == 5)
        {
            definition.spriteId = var1.getSigned(DataType.INT).toInt()
            definition.alternateSpriteId = var1.getSigned(DataType.INT).toInt()
        }
        if (definition.type == 6)
        {
            definition.modelType = 1
            definition.modelId = var1.getUnsigned(DataType.SHORT).toInt()
            if (definition.modelId == 0xFFFF)
            {
                definition.modelId = -1
            }
            definition.alternateModelId = var1.getUnsigned(DataType.SHORT).toInt()
            if (definition.alternateModelId == 0xFFFF)
            {
                definition.alternateModelId = -1
            }
            definition.animation = var1.getUnsigned(DataType.SHORT).toInt()
            if (definition.animation == 0xFFFF)
            {
                definition.animation = -1
            }
            definition.alternateAnimation = var1.getUnsigned(DataType.SHORT).toInt()
            if (definition.alternateAnimation == 0xFFFF)
            {
                definition.alternateAnimation = -1
            }
            definition.modelZoom = var1.getUnsigned(DataType.SHORT).toInt()
            definition.rotationX = var1.getUnsigned(DataType.SHORT).toInt()
            definition.rotationZ = var1.getUnsigned(DataType.SHORT).toInt()
        }
        if (definition.type == 7)
        {
            definition.itemIds = IntArray(definition.originalWidth * definition.originalHeight)
            definition.itemQuantities = IntArray(definition.originalWidth * definition.originalHeight)
            definition.xTextAlignment = var1.getUnsigned(DataType.BYTE).toInt()
            definition.fontId = var1.getUnsigned(DataType.SHORT).toInt()
            if (definition.fontId == 0xFFFF) {
                definition.fontId = -1
            }
            definition.textShadowed = var1.getUnsigned(DataType.BYTE).toInt() == 1
            definition.textColor = var1.getSigned(DataType.INT).toInt()
            definition.xPitch = var1.getSigned(DataType.SHORT).toInt()
            definition.yPitch = var1.getSigned(DataType.SHORT).toInt()
            var4 = var1.getUnsigned(DataType.BYTE).toInt()
            if (var4 == 1) {
                definition.clickMask = definition.clickMask or 1073741824
            }
            definition.configActions = arrayOfNulls(5)
            var5 = 0
            while (var5 < 5) {
                val var10 = var1.string
                if (var10.length > 0)
                {
                    definition.configActions[var5] = var10
                    definition.clickMask = definition.clickMask or (1 shl var5 + 23)
                }
                ++var5
            }
        }
        if (definition.type == 8)
        {
            definition.text = var1.string
        }
        if (definition.menuType == 2 || definition.type == 2)
        {
            definition.targetVerb = var1.string
            definition.spellName = var1.string
            var4 = var1.getUnsigned(DataType.SHORT).toInt() and 63
            definition.clickMask = definition.clickMask or (var4 shl 11)
        }
        if (definition.menuType == 1 || definition.menuType == 4 || definition.menuType == 5 || definition.menuType == 6)
        {
            definition.tooltip = var1.string
            if (definition.tooltip.length == 0)
            {
                if (definition.menuType == 1)
                {
                    definition.tooltip = "Ok"
                }
                if (definition.menuType == 4)
                {
                    definition.tooltip = "Select"
                }
                if (definition.menuType == 5)
                {
                    definition.tooltip = "Select"
                }
                if (definition.menuType == 6)
                {
                    definition.tooltip = "Continue"
                }
            }
        }
        if (definition.menuType == 1 || definition.menuType == 4 || definition.menuType == 5)
        {
            definition.clickMask = definition.clickMask or 4194304
        }
        if (definition.menuType == 6)
        {
            definition.clickMask = definition.clickMask or 1
        }
    }

    private fun decodeIf3(definition: WidgetDefinition, var1: BufferedReader)
    {
        var1.getUnsigned(DataType.BYTE).toInt()
        definition.isIf3 = true
        definition.type = var1.getUnsigned(DataType.BYTE).toInt()
        definition.contentType = var1.getUnsigned(DataType.SHORT).toInt()
        definition.originalX = var1.getSigned(DataType.SHORT).toInt()
        definition.originalY = var1.getSigned(DataType.SHORT).toInt()
        definition.originalWidth = var1.getUnsigned(DataType.SHORT).toInt()
        if (definition.type == 9)
        {
            definition.originalHeight = var1.getSigned(DataType.SHORT).toInt()
        } else
        {
            definition.originalHeight = var1.getUnsigned(DataType.SHORT).toInt()
        }
        definition.widthMode = var1.getSigned(DataType.BYTE).toInt()
        definition.heightMode = var1.getSigned(DataType.BYTE).toInt()
        definition.xPositionMode = var1.getSigned(DataType.BYTE).toInt()
        definition.yPositionMode = var1.getSigned(DataType.BYTE).toInt()
        definition.parentId = var1.getUnsigned(DataType.SHORT).toInt()
        if (definition.parentId == 0xFFFF)
        {
            definition.parentId = -1
        } else
        {
            definition.parentId += definition.componentId and 0xFFFF.inv()
        }
        definition.isHidden = var1.getUnsigned(DataType.BYTE).toInt() == 1
        if (definition.type == 0)
        {
            definition.scrollWidth = var1.getUnsigned(DataType.SHORT).toInt()
            definition.scrollHeight = var1.getUnsigned(DataType.SHORT).toInt()
            definition.noClickThrough = var1.getUnsigned(DataType.BYTE).toInt() == 1
        }
        if (definition.type == 5)
        {
            definition.spriteId = var1.getSigned(DataType.INT).toInt()
            definition.textureId = var1.getUnsigned(DataType.SHORT).toInt()
            definition.spriteTiling = var1.getUnsigned(DataType.BYTE).toInt() == 1
            definition.opacity = var1.getUnsigned(DataType.BYTE).toInt()
            definition.borderType = var1.getUnsigned(DataType.BYTE).toInt()
            definition.shadowColor = var1.getSigned(DataType.INT).toInt()
            definition.flippedVertically = var1.getUnsigned(DataType.BYTE).toInt() == 1
            definition.flippedHorizontally = var1.getUnsigned(DataType.BYTE).toInt() == 1
        }
        if (definition.type == 6) {
            definition.modelType = 1
            definition.modelId = var1.getUnsigned(DataType.SHORT).toInt()
            if (definition.modelId == 0xFFFF) {
                definition.modelId = -1
            }
            definition.offsetX2d = var1.getSigned(DataType.SHORT).toInt()
            definition.offsetY2d = var1.getSigned(DataType.SHORT).toInt()
            definition.rotationX = var1.getUnsigned(DataType.SHORT).toInt()
            definition.rotationZ = var1.getUnsigned(DataType.SHORT).toInt()
            definition.rotationY = var1.getUnsigned(DataType.SHORT).toInt()
            definition.modelZoom = var1.getUnsigned(DataType.SHORT).toInt()
            definition.animation = var1.getUnsigned(DataType.SHORT).toInt()
            if (definition.animation == 0xFFFF) {
                definition.animation = -1
            }
            definition.orthogonal = var1.getUnsigned(DataType.BYTE).toInt() == 1
            var1.getUnsigned(DataType.SHORT).toInt()
            if (definition.widthMode != 0)
            {
                definition.modelHeightOverride = var1.getUnsigned(DataType.SHORT).toInt()
            }
            if (definition.heightMode != 0)
            {
                var1.getUnsigned(DataType.SHORT).toInt()
            }
        }
        if (definition.type == 4)
        {
            definition.fontId = var1.getUnsigned(DataType.SHORT).toInt()
            if (definition.fontId == 0xFFFF)
            {
                definition.fontId = -1
            }
            definition.text = var1.string
            definition.lineHeight = var1.getUnsigned(DataType.BYTE).toInt()
            definition.xTextAlignment = var1.getUnsigned(DataType.BYTE).toInt()
            definition.yTextAlignment = var1.getUnsigned(DataType.BYTE).toInt()
            definition.textShadowed = var1.getUnsigned(DataType.BYTE).toInt() == 1
            definition.textColor = var1.getSigned(DataType.INT).toInt()
        }
        if (definition.type == 3)
        {
            definition.textColor = var1.getSigned(DataType.INT).toInt()
            definition.filled = var1.getUnsigned(DataType.BYTE).toInt() == 1
            definition.opacity = var1.getUnsigned(DataType.BYTE).toInt()
        }
        if (definition.type == 9)
        {
            definition.lineWidth = var1.getUnsigned(DataType.BYTE).toInt()
            definition.textColor = var1.getSigned(DataType.INT).toInt()
            definition.lineDirection = var1.getUnsigned(DataType.BYTE).toInt() == 1
        }
        definition.clickMask = read24BitInt(var1)
        definition.name = var1.string
        val var2 = var1.getUnsigned(DataType.BYTE).toInt()
        if (var2 > 0)
        {
            definition.actions = arrayOfNulls(var2)
            for (var3 in 0 until var2)
            {
                definition.actions[var3] = var1.string
            }
        }
        definition.dragDeadZone = var1.getUnsigned(DataType.BYTE).toInt()
        definition.dragDeadTime = var1.getUnsigned(DataType.BYTE).toInt()
        definition.dragRenderBehavior = var1.getUnsigned(DataType.BYTE).toInt() == 1
        definition.targetVerb = var1.string
        definition.onLoadListener = decodeListener(definition, var1)
        definition.onMouseOverListener = decodeListener(definition, var1)
        definition.onMouseLeaveListener = decodeListener(definition, var1)
        definition.onTargetLeaveListener = decodeListener(definition, var1)
        definition.onTargetEnterListener = decodeListener(definition, var1)
        definition.onVarTransmitListener = decodeListener(definition, var1)
        definition.onInvTransmitListener = decodeListener(definition, var1)
        definition.onStatTransmitListener = decodeListener(definition, var1)
        definition.onTimerListener = decodeListener(definition, var1)
        definition.onOpListener = decodeListener(definition, var1)
        definition.onMouseRepeatListener = decodeListener(definition, var1)
        definition.onClickListener = decodeListener(definition, var1)
        definition.onClickRepeatListener = decodeListener(definition, var1)
        definition.onReleaseListener = decodeListener(definition, var1)
        definition.onHoldListener = decodeListener(definition, var1)
        definition.onDragListener = decodeListener(definition, var1)
        definition.onDragCompleteListener = decodeListener(definition, var1)
        definition.onScrollWheelListener = decodeListener(definition, var1)
        definition.varTransmitTriggers = decodeTriggers(var1)
        definition.invTransmitTriggers = decodeTriggers(var1)
        definition.statTransmitTriggers = decodeTriggers(var1)
    }

    private fun decodeListener(definition: WidgetDefinition, var1: BufferedReader): Array<Any>
    {
        val var2 = var1.getUnsigned(DataType.BYTE).toInt()
        return arrayOf(
            if (var2 == 0)
            {
                emptyArray()
            } else
            {
                val var3 = arrayOfNulls<Any>(var2)
                for (var4 in 0 until var2)
                {
                    val var5 = var1.getUnsigned(DataType.BYTE).toInt()
                    if (var5 == 0)
                    {
                        var3[var4] = var1.getSigned(DataType.INT).toInt()
                    } else if (var5 == 1)
                    {
                        var3[var4] = var1.string
                    }
                }
                definition.hasListener = true
                var3
            }
        )
    }

    private fun decodeTriggers(var1: BufferedReader): IntArray
    {
        val var2 = var1.getUnsigned(DataType.BYTE).toInt()
        return if (var2 == 0)
        {
            IntArray(0)
        } else
        {
            val var3 = IntArray(var2)
            for (var4 in 0 until var2)
            {
                var3[var4] = var1.getSigned(DataType.INT).toInt()
            }
            var3
        }
    }

    fun read24BitInt(reader: BufferedReader): Int
    {
        return ((reader.getUnsigned(DataType.BYTE) shl 16) + (reader.getUnsigned(DataType.BYTE) shl 8) + reader.getUnsigned(DataType.BYTE)).toInt()
    }

}