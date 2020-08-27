package rs.emulator.plugin

import org.pf4j.Plugin
import org.pf4j.PluginWrapper
import rs.emulator.entity.actor.player.widgets.WidgetViewport
import rs.emulator.widget.WidgetRegistration

/**
 *
 * @author javatar
 */

class GameFramePlugin(wrapper: PluginWrapper) : Plugin(wrapper) {

    override fun start() {
        super.start()
        WidgetRegistration.registerWidget(548, "fixed-gameframe") {
            components {
                button(37) {
                    it.source.widgetViewport.open(182, WidgetViewport.OverlayFrame.TABS)
                }
                button(54) {
                    it.source.widgetViewport.open(149, WidgetViewport.OverlayFrame.TABS)
                }
                button(55) {
                    it.source.widgetViewport.open(387, WidgetViewport.OverlayFrame.TABS)
                }
            }

        }
        WidgetRegistration.registerWidget(182, "fixed-gameframe") {
            components {
                button(8) {
                    it.source.logout()
                }
            }
        }
        WidgetRegistration.registerWidget(160, "fixed-gameframe") {
            components {
                button(46) {
                    it.source.messages().sendChatMessage("Worked!")
                }
            }
        }
    }

    override fun stop() {
        super.stop()
        WidgetRegistration.deregisterWidget(548)
        WidgetRegistration.deregisterWidget(182)
        WidgetRegistration.deregisterWidget(160)
    }
}