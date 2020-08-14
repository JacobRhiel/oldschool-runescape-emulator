package rs.emulator.plugins.extensions.factories.actions

import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface SaveAction {

    fun onSave(player : IPlayer)
    fun onLoad(player : IPlayer)

}