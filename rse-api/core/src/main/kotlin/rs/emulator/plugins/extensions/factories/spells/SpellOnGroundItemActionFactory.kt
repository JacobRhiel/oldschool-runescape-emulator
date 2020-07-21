package rs.emulator.plugins.extensions.factories.spells

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.spell.SpellOnGroundItemAction

/**
 *
 * @author javatar
 */

interface SpellOnGroundItemActionFactory : ExtensionPoint {

    fun registerSpellOnGroundItemActions(
        itemId: Int,
        x: Int,
        y: Int,
        spellChildIndex: Int,
        widgetId: Int,
        widgetChildId: Int,
        controlPressed: Boolean
    ): SpellOnGroundItemAction

}