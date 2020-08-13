package rs.emulator.definitions.enums

import it.unimi.dsi.fastutil.ints.Int2ReferenceArrayMap
import rs.emulator.definitions.Definition
import rs.emulator.definitions.scripts.ScriptVarType

/**
 *
 * @author Chk
 */
data class EnumDefinition(val identifier: Int,
                          var intValues: IntArray = intArrayOf(),
                          var keyType: ScriptVarType? = null,
                          var valType: ScriptVarType? = null,
                          var defaultString: String = "null",
                          var defaultInt: Int = 0,
                          var size: Int = 0,
                          var keys: IntArray = intArrayOf(),
                          var stringValues: Int2ReferenceArrayMap<String> = Int2ReferenceArrayMap<String>()
) : Definition(identifier)
{

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EnumDefinition

        if (identifier != other.identifier) return false
        if (!intValues.contentEquals(other.intValues)) return false
        if (keyType != other.keyType) return false
        if (valType != other.valType) return false
        if (defaultString != other.defaultString) return false
        if (defaultInt != other.defaultInt) return false
        if (size != other.size) return false
        if (!keys.contentEquals(other.keys)) return false
        if (stringValues != other.stringValues) return false

        return true
    }

    override fun hashCode(): Int
    {
        var result = identifier
        result = 31 * result + intValues.contentHashCode()
        result = 31 * result + (keyType?.hashCode() ?: 0)
        result = 31 * result + (valType?.hashCode() ?: 0)
        result = 31 * result + defaultString.hashCode()
        result = 31 * result + defaultInt
        result = 31 * result + size
        result = 31 * result + keys.contentHashCode()
        result = 31 * result + stringValues.hashCode()
        return result
    }

}