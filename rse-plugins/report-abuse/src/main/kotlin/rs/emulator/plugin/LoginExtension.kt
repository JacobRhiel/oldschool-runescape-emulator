package rs.emulator.plugin

import org.pf4j.Extension
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.plugins.extensions.factories.LoginActionFactory
import rs.emulator.plugins.extensions.factories.actions.LoginAction

/**
 *
 * @author javatar
 */
@Extension(plugins = ["REPORT-ABUSE"])
class LoginExtension : LoginActionFactory, LoginAction {
    override fun registerLoginAction(player: IPlayer): LoginAction {
        return this
    }

    override fun onLogin(player: IPlayer) {

        /*val comp = player.widgetViewport.getDynamicComponent(WidgetViewport.Frames.COMMUNICATION_HUB)

        val chat = comp.open(Component(162))



        player.widgetViewport.apply {
            this[162] = ChatWidget()
        }
        player.widgetViewport[162][33].active = true
        player.widgetViewport[162][33].subscribe<ComponentClickEvent> {
            if (it.option == 78) {
                player.widgetViewport[548][23].open(Component(553)) {
                    player.messages().sendClientScript(1104, 1, 1)
                }
            }
        }*/
    }
}