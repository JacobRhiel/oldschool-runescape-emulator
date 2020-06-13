package rs.emulator.cache.definition.varp.bit

import rs.emulator.cache.definition.Definition

/**
 *
 * @author Chk
 */
data class VarBitDefinition(val identifier: Int,
                            var index: Int = 0,
                            var leastSignificantBit: Int = 0,
                            var mostSignificantBit: Int = 0
) : Definition(identifier)