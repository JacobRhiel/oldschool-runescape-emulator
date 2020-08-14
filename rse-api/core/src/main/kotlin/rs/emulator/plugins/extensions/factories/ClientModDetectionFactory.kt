package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.plugins.extensions.factories.actions.ClientModDetectionAction

/**
 *
 * @author javatar
 */

interface ClientModDetectionFactory : ExtensionPoint {

    fun registerClientModDetection(
        player: IPlayer
    ) : ClientModDetectionAction

}