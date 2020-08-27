package rs.emulator.plugin

import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.pf4j.Plugin
import org.pf4j.PluginWrapper
import rs.emulator.entity.actor.attributes.attribute.BooleanAttributeValue
import rs.emulator.entity.actor.player.widgets.WidgetEvent
import rs.emulator.entity.actor.player.widgets.WidgetViewport
import rs.emulator.reactive.launch
import rs.emulator.region.as30BitInteger
import rs.emulator.widget.WidgetRegistration

/**
 *
 * @author javatar
 */

class GameFramePlugin(wrapper: PluginWrapper) : Plugin(wrapper) {

    override fun start() {
        super.start()

        WidgetRegistration.registerWidget(548, "fixed-gameframe") {
            addActionComponent(37) {
                it.source.widgetViewport.open(182, WidgetViewport.OverlayFrame.TABS)
            }
            addActionComponent(54) {
                it.source.widgetViewport.open(149, WidgetViewport.OverlayFrame.TABS)
            }
            addActionComponent(55) {
                it.source.widgetViewport.open(387, WidgetViewport.OverlayFrame.TABS)
            }
        }

        WidgetRegistration.registerWidget(182, "fixed-gameframe") {
            addActionComponent(8) {
                it.source.logout()
            }
        }

        /*WidgetRegistration.registerWidget(595, "world-map") {

            addActionComponent(38) {
                it.source.widgetViewport.close(WidgetViewport.OverlayFrame.VIEW_PORT)
            }

        }*/

        WidgetRegistration.registerWidget(160, "fixed-gameframe") {

            addActionComponent(22) {

                it.source.running = !it.source.running

            }

            addActionComponent(46) {
                it.source.messages().sendClientScript(1749, it.source.coordinate.as30BitInteger)
                it.source.widgetViewport.open(595, WidgetViewport.OverlayFrame.VIEW_PORT)
                it.source.messages().sendAccessMask(595, 17, 0..4, WidgetEvent.IF_BUTTON1)

                /*val player = it.source

                player.coordinateState.events.consume {

                    println("is interface open: " + player.widgetViewport.isWidgetActive(595))

                    if(player.widgetViewport.isWidgetActive(595))
                        player.messages().sendClientScript(1749, player.coordinate.as30BitInteger)

                }*/

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