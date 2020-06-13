package rs.emulator.cache.definition.widget.inv

import rs.emulator.cache.definition.Definition

/**
 *
 * @author Chk
 */
data class InvDefinition(val identifier: Int,
                         var size: Int = 0
) : Definition(identifier)