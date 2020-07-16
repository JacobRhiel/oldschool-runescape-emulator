package rs.emulator.plugins.extensions.factories.entity.actions

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.action.NpcAction

/**
 *
 * @author javatar
 */

interface NpcActionFactory : ExtensionPoint {

    fun registerNpcActions(npcIndex: Int, option: Int, controlPressed: Boolean): NpcAction

}