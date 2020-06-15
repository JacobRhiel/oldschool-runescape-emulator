package rs.emulator.cache.definition.widget.struct

import rs.emulator.cache.definition.Definition
import rs.emulator.cache.definition.widget.script.ScriptVarType

/**
 *
 * @author Chk
 */
data class StructDefinition(val identifier: Int,
                            var params: HashMap<Int, Any>? = hashMapOf()
) : Definition(identifier)