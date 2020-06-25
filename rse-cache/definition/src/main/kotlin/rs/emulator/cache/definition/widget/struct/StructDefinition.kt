package rs.emulator.cache.definition.widget.struct

import rs.emulator.definitions.Definition

/**
 *
 * @author Chk
 */
data class StructDefinition(val identifier: Int,
                            var params: HashMap<Int, Any>? = hashMapOf()
) : Definition(identifier)