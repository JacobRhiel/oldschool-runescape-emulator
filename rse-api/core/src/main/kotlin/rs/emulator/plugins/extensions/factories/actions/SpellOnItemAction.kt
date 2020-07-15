package rs.emulator.plugins.extensions.factories.actions

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.temp.ISpell

/**
 *
 * @author javatar
 */

interface SpellOnItemAction {

    fun handleSpellOnItem(
        player: IPlayer,
        spell: ISpell,
        itemId: Int
    )

}