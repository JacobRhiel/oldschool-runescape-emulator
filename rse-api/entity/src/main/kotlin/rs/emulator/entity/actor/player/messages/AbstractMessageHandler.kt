package rs.emulator.entity.actor.player.messages

/**
 *
 * @author javatar
 */

abstract class AbstractMessageHandler :
    IVarpMessage,
    IContainerMessages,
    IWidgetMessages,
    IMessages {


    inline fun <reified M : IMessages> ofType(): M {
        return this as M
    }


}