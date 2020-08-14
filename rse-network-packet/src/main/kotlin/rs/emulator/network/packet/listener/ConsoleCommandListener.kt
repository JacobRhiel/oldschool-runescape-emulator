package rs.emulator.network.packet.listener

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ConsoleCommandMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.CommandFactory
import rs.emulator.utilities.contexts.scopes.ActorScope
import rs.emulator.utilities.koin.get

/**
 *
 * @author Chk
 */
class ConsoleCommandListener : GamePacketListener<ConsoleCommandMessage>
{

    override fun handle(
        player: Player,
        message: ConsoleCommandMessage
    )
    {
        flowOf(*RSPluginManager.getExtensions<CommandFactory>().toTypedArray())
            .filter { it.hasRights(player) }
            .onEach { it.execute(player, message.args) }
            .launchIn(get<ActorScope>())
    }

}