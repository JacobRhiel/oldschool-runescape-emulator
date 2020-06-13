package rs.emulator.cache.definition.entity.npc

import rs.emulator.cache.definition.Definition

/**
 *
 * @author Chk
 */
data class NpcDefinition(val identifier: Int,
                         var name: String = "",
                         var size: Int = 1,
                         var standAnim: Int = -1,
                         var walkAnim: Int = -1,
                         var render3: Int = -1,
                         var render4: Int = -1,
                         var render5: Int = -1,
                         var render6: Int = -1,
                         var render7: Int = -1,
                         var visibleMapDot: Boolean = true,
                         var combatLevel: Int = -1,
                         var width: Int = -1,
                         var length: Int = -1,
                         var render: Boolean = false,
                         var headIcon: Int = -1,
                         var varp: Int = -1,
                         var varbit: Int = -1,
                         var interactable: Boolean = true,
                         var pet: Boolean = false,
                         var options: Array<String?> = Array(5) { "" },
                         var transforms: Array<Int>? = null,
                         var examine: String? = null
) : Definition(identifier)
{

    fun isAttackable(): Boolean = combatLevel > 0 && options.any { it == "Attack" }

}