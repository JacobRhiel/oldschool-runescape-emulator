package rs.emulator.cache.definition.entity.idk

import rs.emulator.cache.definition.Definition

/**
 *
 * @author Chk
 */
data class IdentityKitDefinition(val identifier: Int,
                                 var recolorToReplace: ShortArray = shortArrayOf(),
                                 var recolorToFind: ShortArray = shortArrayOf(),
                                 var retextureToFind: ShortArray = shortArrayOf(),
                                 var retextureToReplace: ShortArray = shortArrayOf(),
                                 var bodyPartId: Int = -1,
                                 var models: IntArray = intArrayOf(),
                                 var chatheadModels: IntArray = intArrayOf(-1, -1, -1, -1, -1),
                                 var nonSelectable: Boolean = false
) : Definition(identifier)