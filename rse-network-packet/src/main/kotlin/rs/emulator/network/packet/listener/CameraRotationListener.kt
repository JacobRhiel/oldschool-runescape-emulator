package rs.emulator.network.packet.listener

import io.reactivex.rxkotlin.toObservable
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.CameraRotationMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.CameraRotationActionFactory

/**
 *
 * @author javatar
 */

class CameraRotationListener : GamePacketListener<CameraRotationMessage> {
    override fun handle(
        player: Player,
        message: CameraRotationMessage
    ) {

        RSPluginManager.getExtensions<CameraRotationActionFactory>()
            .toObservable()
            .map {
                it.registerCameraRotationActions(
                    message.camAngleX,
                    message.camAngleY
                )
            }.subscribe({
                it.handleCameraRotation(message.camAngleX, message.camAngleY)
            }, {
                it.printStackTrace()
            }).dispose()
    }
}