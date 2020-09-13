package rs.emulator.network.packet.listener

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ReportAbuseMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.ReportAbuseFactory
import rs.emulator.utilities.contexts.scopes.ActorScope
import rs.emulator.utilities.koin.get

/**
 *
 * @author javatar
 */

class ReportAbuseListener : GamePacketListener<ReportAbuseMessage> {
    override fun handle(
        player: Player,
        message: ReportAbuseMessage
    ) {

        flowOf(*RSPluginManager.getExtensions<ReportAbuseFactory>().toTypedArray())
            .map { it.registerReportAbuseAction(message.name, message.abuseType, message.isStaff) }
            .onEach { it.handleReportedAbuse(player, message.name, message.abuseType, message.isStaff) }
            .launchIn(get<ActorScope>())

    }
}