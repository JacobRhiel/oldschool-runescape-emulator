package rs.emulator.cache.definition.entity.loc

import rs.emulator.cache.definition.Definition

/**
 *
 * @author Chk
 */
data class LocDefinition(val identifier: Int,
                         var name: String = "",
                         var width: Int = 1,
                         var length: Int = 1,
                         var solid: Boolean = true,
                         var impenetrable: Boolean = true,
                         var interactive: Boolean = false,
                         var obstructive: Boolean = false,
                         var clipMask: Int = 0,
                         var varbit: Int = -1,
                         var varp: Int = -1,
                         var animation: Int = -1,
                         var rotated: Boolean = false,
                         val options: Array<String?> = Array(5) { "" },
                         var transforms: Array<Int>? = null,
                         var examine: String? = null
) : Definition(identifier)