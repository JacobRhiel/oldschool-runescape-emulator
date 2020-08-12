package rs.emulator.plugin

import org.pf4j.Plugin
import org.pf4j.PluginWrapper
import rs.emulator.entity.actor.player.widgets.WidgetViewport
import rs.emulator.widget.WidgetRegistration

/**
 *
 * @author javatar
 */

class ReportAbusePlugin(wrapper: PluginWrapper) : Plugin(wrapper) {
    override fun start() {
        super.start()
        WidgetRegistration.registerWidget(162) {
            addActionComponent(33) {
                println("clicked ${it.option}")
                val player = it.source
                if(it.option == 78) {
                    player.widgetViewport.open(553, WidgetViewport.OverlayFrame.VIEW_PORT)
                    player.messages().sendClientScript(1104, 1, 1)
                }
            }
        }
        assert(WidgetRegistration.assertWidgetIsRegistered(162))
    }

    override fun stop() {
        WidgetRegistration.deregisterWidget(162)
    }
}