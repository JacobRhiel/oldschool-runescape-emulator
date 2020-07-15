package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class CameraRotationMessage(val camAngleX: Int, val camAngleY: Int) : GamePacketMessage(17)