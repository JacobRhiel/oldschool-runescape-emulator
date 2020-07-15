package rs.emulator.entity.actor.player.messages

/**
 *
 * @author javatar
 */

interface IWidgetMessages : IMessages {

    fun sendClientScript(scriptId: Int, vararg params: Any)
    fun sendOpenOverlay(id: Int)
    fun sendOpenSub(parentId: Int, childId: Int, component: Int, interType: Int)
    fun sendDisplayWidgetUpdate()
    fun sendChatMessage(message: String, messageType: Int = 0) //TODO - refactor message type to enum

}