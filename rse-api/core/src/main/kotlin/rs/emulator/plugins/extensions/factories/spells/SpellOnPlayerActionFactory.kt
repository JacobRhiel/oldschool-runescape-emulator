package rs.emulator.plugins.extensions.factories.spells

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.spell.SpellOnPlayerAction

/**
 *
 * @author javatar
 */

interface SpellOnPlayerActionFactory : ExtensionPoint {

    fun registerSpellOnPlayerAction(
        playerIndex: Int,
        spellChildIndex: Int,
        spellWidgetId: Int,
        spellWidgetChildId: Int,
        controlPressed: Boolean
    ): SpellOnPlayerAction

}