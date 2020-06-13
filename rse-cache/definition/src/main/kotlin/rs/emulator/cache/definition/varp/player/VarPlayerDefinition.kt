package rs.emulator.cache.definition.varp.player

import rs.emulator.cache.definition.Definition

/**
 *
 * @author Chk
 */
data class VarPlayerDefinition(val identifier: Int,
                               var configType: Int = 0
) : Definition(identifier)