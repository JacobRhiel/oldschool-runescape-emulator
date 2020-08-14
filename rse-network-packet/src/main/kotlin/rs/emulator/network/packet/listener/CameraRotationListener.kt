package rs.emulator.network.packet.listener

import io.reactivex.rxkotlin.toObservable
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.CameraRotationMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.CameraRotationActionFactory
import rs.emulator.utilities.contexts.scopes.ActorScope
import rs.emulator.utilities.koin.get

/**
 *
 * @author javatar
 */

class CameraRotationListener : GamePacketListener<CameraRotationMessage> {
    override fun handle(
        player: Player,
        message: CameraRotationMessage
    ) {

        flowOf(*RSPluginManager.getExtensions<CameraRotationActionFactory>().toTypedArray())
            .map { it.registerCameraRotationActions(message.camAngleX, message.camAngleY) }
            .onEach { it.handleCameraRotation(message.camAngleX, message.camAngleY) }
            .launchIn(get<ActorScope>())
    }
}