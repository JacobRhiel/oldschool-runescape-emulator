package rs.emulator.plugins.extensions.factories.actions.spell

import rs.emulator.entity.actor.npc.INpc
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.temp.ISpell

/**
 *
 * @author javatar
 */

interface SpellOnNpcAction {

    fun handleSpellOnNpc(player: IPlayer, npc: INpc, spell: ISpell)

}