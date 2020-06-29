package rs.emulator.entity.actor.player.messages

/**
 *
 * @author javatar
 */

interface IWidgetMessages {

    fun sendClientScript(scriptId : Int, vararg params : Any)
    fun sendOpenOverlay(id : Int)
    fun sendOpenSub(parentId : Int, childId : Int, component : Int, interType : Int)
    fun sendDisplayWidgetUpdate()

}