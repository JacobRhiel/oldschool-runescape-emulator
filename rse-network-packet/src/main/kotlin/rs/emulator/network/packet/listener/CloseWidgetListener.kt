package rs.emulator.network.packet.listener

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import rs.emulator.entity.actor.player.widgets.WidgetViewport
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.CloseWidgetMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.widgets.CloseWidgetFactory
import rs.emulator.utilities.contexts.scopes.ActorScope
import rs.emulator.utilities.koin.get


/**
 *
 * @author javatar
 */
@ExperimentalCoroutinesApi
class CloseWidgetListener : GamePacketListener<CloseWidgetMessage> {
    override fun handle(
        player: Player,
        message: CloseWidgetMessage
    ) {

        player.widgetViewport.close(WidgetViewport.OverlayFrame.VIEW_PORT)

        flowOf(*RSPluginManager.getExtensions<CloseWidgetFactory>().toTypedArray())
            .map { it.registerClosingWidgetAction(player) }
            .onEach { it.handleCloseWidgetRequest(player) }
            .launchIn(get<ActorScope>())

    }
}