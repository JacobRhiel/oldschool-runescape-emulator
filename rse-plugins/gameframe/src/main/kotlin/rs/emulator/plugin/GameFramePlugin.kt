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
        /*WidgetRegistration.registerWidget(548, "fixed-gameframe") {
            addActionComponent(37) {
                it.source.widgetViewport.open(182, WidgetViewport.OverlayFrame.TABS)
            }
            addActionComponent(54) {
                it.source.widgetViewport.open(149, WidgetViewport.OverlayFrame.TABS)
            }
            addActionComponent(55) {
                it.source.widgetViewport.open(387, WidgetViewport.OverlayFrame.TABS)
            }
        }*/
    }

    override fun stop() {
        super.stop()
        WidgetRegistration.deregisterWidget(548)
    }
}