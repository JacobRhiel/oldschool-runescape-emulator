package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.NpcActions

/**
 *
 * @author javatar
 */

interface NpcActionFactory : ExtensionPoint {

    fun registerNpcActions(npcIndex: Int, option: Int, controlPressed: Boolean): NpcActions

}