package rs.emulator.network.packet.atest.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class GrandExchangeOfferMessage(
    val slot: Int,
    val state: Int,
    val itemId: Int,
    val price: Int,
    val totalQuantity: Int,
    val quantitySold: Int,
    val totalSpent: Int
) : GamePacketMessage(8)