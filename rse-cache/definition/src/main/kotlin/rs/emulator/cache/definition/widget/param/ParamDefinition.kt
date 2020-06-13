package rs.emulator.cache.definition.widget.param

import rs.emulator.cache.definition.Definition
import rs.emulator.cache.definition.widget.script.ScriptVarType

/**
 *
 * @author Chk
 */
data class ParamDefinition(val identifier: Int,
                           var type: ScriptVarType? = null,
                           var isMembers: Boolean = true,
                           var defaultInt: Int = 0,
                           var defaultString: String? = null
) : Definition(identifier)