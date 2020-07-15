package rs.emulator.entity.actor.player.messages

/**
 *
 * @author javatar
 */

interface IVarpMessage : IMessages {

    fun sendSmallVarp(id: Int, value: Int)
    fun sendLargeVarp(id: Int, value: Int)

}