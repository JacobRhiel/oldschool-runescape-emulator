package rs.emulator.cache.definition.widget.inv

import rs.emulator.definitions.Definition

/**
 *
 * @author Chk
 */
data class InvDefinition(val identifier: Int,
                         var size: Int = 0
) : Definition(identifier)