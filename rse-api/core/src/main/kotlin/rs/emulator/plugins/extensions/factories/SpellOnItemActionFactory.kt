package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.spell.SpellOnItemAction

/**
 *
 * @author javatar
 */

interface SpellOnItemActionFactory : ExtensionPoint {

    fun registerSpellOnItemActions(
        itemId: Int,
        slot: Int,
        widgetId: Int,
        widgetChildId: Int,
        spellWidgetId: Int,
        spellWidgetChildId: Int,
        spellChildIndex: Int
    ): SpellOnItemAction

}