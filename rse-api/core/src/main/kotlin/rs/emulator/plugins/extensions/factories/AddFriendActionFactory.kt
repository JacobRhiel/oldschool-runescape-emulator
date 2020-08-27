package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.plugins.extensions.factories.actions.ClientModDetectionAction
import rs.emulator.plugins.extensions.factories.actions.social.AddFriendAction

/**
 *
 * @author Chk
 */
interface AddFriendActionFactory : ExtensionPoint
{

    fun registerAddFriend(
        player: IPlayer
    ) : AddFriendAction

}