package rs.emulator.network.packet.listener

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ClientModDetectionMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.ClientModDetectionFactory
import rs.emulator.utilities.contexts.scopes.ActorScope
import rs.emulator.utilities.koin.get

/**
 *
 * @author Chk
 */
class ClientModDetectionListener : GamePacketListener<ClientModDetectionMessage>
{

    override fun handle(
        player: Player,
        message: ClientModDetectionMessage
    )
    {

        flowOf(*RSPluginManager.getExtensions<ClientModDetectionFactory>().toTypedArray())
            .map { it.registerClientModDetection(player) }
            .onEach { it.handleModificationDetected(player) }
            .launchIn(get<ActorScope>())

    }

}