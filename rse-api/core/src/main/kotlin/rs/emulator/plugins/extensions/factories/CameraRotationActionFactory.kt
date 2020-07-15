package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.CameraRotationAction

/**
 *
 * @author javatar
 */

interface CameraRotationActionFactory : ExtensionPoint {

    fun registerCameraRotationActions(
        angleX: Int,
        angleY: Int
    ): CameraRotationAction

}