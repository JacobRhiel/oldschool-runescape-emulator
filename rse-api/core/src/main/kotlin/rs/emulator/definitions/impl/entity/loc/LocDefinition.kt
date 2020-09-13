package rs.emulator.definitions.impl.entity.loc

import rs.emulator.definitions.Definition

/**
 *
 * @author Chk
 */
data class LocDefinition(val identifier: Int,
                         var name: String = "",
                         var sizeX: Int = 1,
                         var sizeY: Int = 1,
                         var projectileClipped: Boolean = true,
                         var solidType: Int = 2,
                         var isWallOrDoor: Boolean = false,
                         var obstructive: Boolean = false,
                         var clipMask: Int = 0,
                         var varbit: Int = -1,
                         var varp: Int = -1,
                         var animation: Int = -1,
                         var rotated: Boolean = false,
                         val options: Array<String?> = Array(5) { "" },
                         var transforms: Array<Int>? = null,
                         var examine: String? = null,
                         var isSolid: Boolean = false
) : Definition(identifier)