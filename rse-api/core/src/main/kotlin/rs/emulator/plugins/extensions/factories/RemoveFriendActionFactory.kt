package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.plugins.extensions.factories.actions.social.RemoveFriendAction

/**
 *
 * @author Chk
 */
interface RemoveFriendActionFactory : ExtensionPoint
{

    fun registerRemoveFriend(
        player: IPlayer
    ) : RemoveFriendAction

}