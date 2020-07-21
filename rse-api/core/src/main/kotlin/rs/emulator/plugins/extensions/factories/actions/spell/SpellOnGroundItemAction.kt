package rs.emulator.plugins.extensions.factories.actions.spell

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.temp.ISpell

/**
 *
 * @author javatar
 */

interface SpellOnGroundItemAction {

    fun handleSpellOnGroundItem(player: IPlayer, spell: ISpell, itemId: Int)

}