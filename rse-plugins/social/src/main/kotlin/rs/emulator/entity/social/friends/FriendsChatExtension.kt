package rs.emulator.entity.social.friends

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.pf4j.Extension
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.plugins.extensions.factories.AddFriendActionFactory
import rs.emulator.plugins.extensions.factories.LoginActionFactory
import rs.emulator.plugins.extensions.factories.LogoutActionFactory
import rs.emulator.plugins.extensions.factories.RemoveFriendActionFactory
import rs.emulator.plugins.extensions.factories.actions.LoginAction
import rs.emulator.plugins.extensions.factories.actions.LogoutAction
import rs.emulator.plugins.extensions.factories.actions.social.AddFriendAction
import rs.emulator.plugins.extensions.factories.actions.social.RemoveFriendAction

/**
 *
 * @author Chk
 */
@ExperimentalCoroutinesApi
@Extension(plugins = ["FRIENDS_CHAT-PLUGIN"])
class FriendsChatExtensionLoginActionFactory : LoginAction, LoginActionFactory, LogoutActionFactory, LogoutAction,
    AddFriendActionFactory, AddFriendAction, RemoveFriendActionFactory, RemoveFriendAction
{

    override fun onLogin(player: IPlayer)
    {

        val friends = mutableListOf(player)

        player.messages().sendUpdateFriendsList(friends)

    }

    override fun onLogout(player: IPlayer)
    {

    }

    override fun onAddFriend(player: IPlayer)
    {

        if(player.ignoreList.contains(player))
            return player.messages().sendChatMessage("You must remove this player from your ignore list.")

        if(player.friendsList.contains(player))
            return player.messages().sendChatMessage("Player is already on friends list.")

    }

    override fun onRemoveFriend(player: IPlayer)
    {



    }

    override fun registerLoginAction(player: IPlayer): LoginAction = this

    override fun registerLogoutAction(player: IPlayer): LogoutAction = this

    override fun registerAddFriend(player: IPlayer): AddFriendAction = this

    override fun registerRemoveFriend(player: IPlayer): RemoveFriendAction = this

}