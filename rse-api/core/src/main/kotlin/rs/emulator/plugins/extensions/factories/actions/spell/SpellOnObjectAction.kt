package rs.emulator.plugins.extensions.factories.actions.spell

import rs.emulator.entity.`object`.IObject
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.temp.ISpell

/**
 *
 * @author javatar
 */

interface SpellOnObjectAction {

    fun handleSpellOnObject(player: IPlayer, obj: IObject, spell: ISpell)

}