package rs.emulator.cache.definition.widget.script

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap
import it.unimi.dsi.fastutil.ints.Int2ReferenceArrayMap
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import rs.emulator.cache.definition.Definition
import rs.emulator.cache.definition.widget.script.ScriptVarType

/**
 *
 * @author Chk
 */
data class ScriptDefinition(val identifier: Int,
                            var instructions: IntArray = intArrayOf(),
                            var intOperands: IntArray = intArrayOf(),
                            var stringOperands: Array<String?> = arrayOf(),
                            var intStackCount: Int = 0,
                            var stringStackCount: Int = 0,
                            var localIntCount: Int = 0,
                            var localStringCount: Int = 0,
                            var switches: Int2ReferenceArrayMap<HashMap<Int, Int>> = Int2ReferenceArrayMap(0)
) : Definition(identifier)
{

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScriptDefinition

        if (identifier != other.identifier) return false
        if (!instructions.contentEquals(other.instructions)) return false
        if (!intOperands.contentEquals(other.intOperands)) return false
        if (!stringOperands.contentEquals(other.stringOperands)) return false
        if (intStackCount != other.intStackCount) return false
        if (stringStackCount != other.stringStackCount) return false
        if (localIntCount != other.localIntCount) return false
        if (localStringCount != other.localStringCount) return false
        if (switches != other.switches) return false

        return true
    }

    override fun hashCode(): Int
    {
        var result = identifier
        result = 31 * result + instructions.contentHashCode()
        result = 31 * result + intOperands.contentHashCode()
        result = 31 * result + stringOperands.contentHashCode()
        result = 31 * result + intStackCount
        result = 31 * result + stringStackCount
        result = 31 * result + localIntCount
        result = 31 * result + localStringCount
        result = 31 * result + (switches?.hashCode() ?: 0)
        return result
    }

}