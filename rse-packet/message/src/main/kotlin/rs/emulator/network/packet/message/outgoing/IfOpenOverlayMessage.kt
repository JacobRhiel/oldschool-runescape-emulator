package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
class IfOpenOverlayMessage(val id: Int) : GamePacketMessage(60)