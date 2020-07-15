package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.SpellOnObjectAction

/**
 *
 * @author javatar
 */

interface SpellOnObjectActionFactory : ExtensionPoint {

    fun registerSpellOnObjectActions(
        objectId: Int,
        x: Int,
        y: Int,
        widgetId: Int,
        widgetChildId: Int,
        spellChildIndex: Int,
        controlPressed: Boolean
    ): SpellOnObjectAction

}