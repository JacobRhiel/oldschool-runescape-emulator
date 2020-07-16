package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.spell.SpellOnNpcAction

/**
 *
 * @author javatar
 */

interface SpellOnNpcActionsFactory : ExtensionPoint {

    fun registerSpellOnNpcActions(
        npcIndex: Int,
        spellChildIndex: Int,
        spellWidgetId: Int,
        spellWidgetChildId: Int,
        controlPressed: Boolean
    ): SpellOnNpcAction

}