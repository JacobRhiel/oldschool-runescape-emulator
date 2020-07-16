package rs.emulator.plugins.extensions.factories.actions.action

import rs.emulator.entity.`object`.IObject
import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface ObjectAction {

    fun handleObjectAction(player: IPlayer, obj: IObject, option: Int)

}