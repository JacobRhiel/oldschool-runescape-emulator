package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.plugins.extensions.factories.actions.SaveAction

/**
 *
 * @author javatar
 */

interface SavePlayerFactory : ExtensionPoint {

    fun registerSaveAction(
        player : IPlayer
    ) : SaveAction

}