package rs.emulator.plugin

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.pf4j.Plugin
import org.pf4j.PluginWrapper
import rs.emulator.definitions.factories.WidgetDefinitionFactory
import rs.emulator.definitions.widget.WidgetDefinition
import rs.emulator.entity.actor.player.widgets.WidgetEvent
import rs.emulator.entity.material.containers.bank
import rs.emulator.entity.material.containers.equipment
import rs.emulator.entity.material.containers.inventory
import rs.emulator.identifications.data.VarBits
import rs.emulator.widget.WidgetRegistration

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
class ReportAbusePlugin(wrapper: PluginWrapper) : Plugin(wrapper) {
    override fun start() {
        super.start()
        WidgetRegistration.registerWidget(162, ReportAbusePlugin::class.java.simpleName) {
            components {
                button(33) {
                    val player = it.source
                    if (it.option == 78) {

                        if (player.username() == "hunter23912" || player.username() == "chk") {

                            val bank = player.containerManager.bank
                            bank.updateState()
                            println("Setting up bank")
                            player.messages().sendSmallVarp(115, 0)
                            player.messages().sendSmallVarp(115, 0)
                            player.messages().sendLargeVarp(867, 1034)
                            player.messages().sendLargeVarp(867, 1034)
                            player.messages().sendLargeVarp(867, 1034)
                            player.messages().sendLargeVarp(1052, 1073741824)
                            player.messages().sendLargeVarp(1052, 1073741824)
                            player.messages().sendLargeVarp(1052, 1073741824)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendLargeVarp(1052, 1073741824)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendLargeVarp(1052, 1073741824)
                            player.messages().sendLargeVarp(1052, 1073741824)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendLargeVarp(1052, 1073741824)
                            player.messages().sendLargeVarp(1052, 1073741824)
                            player.messages().sendLargeVarp(1052, 1073741824)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendSmallVarp(1053, 0)
                            player.messages().sendLargeVarp(1052, 1073741824)
                            player.messages().sendLargeVarp(1052, 1073741824)
                            player.messages().sendLargeVarp(1052, 1073741824)
                            player.messages().sendLargeVarp(867, 1034)
                            player.messages().sendSmallVarp(2743, 0)
                            player.messages().sendSmallVarp(2743, 0)
                            player.messages().sendSmallVarp(2743, 0)
                            player.messages().sendSmallVarp(2743, 0)
                            player.messages().sendSmallVarp(2743, 0)
                            player.messages().sendSmallVarp(2743, 0)
                            player.messages().sendSmallVarp(2743, 0)
                            player.messages().sendSmallVarp(2743, 0)
                            player.messages().sendSmallVarp(261, 0)
                            player.messages().sendSmallVarp(262, 2)
                            player.messages().sendItemContainerFull(-1, 207, 95, player.bank())
                            player.messages().sendItemContainerFull(-1, 209, 93, player.inventory())
                            player.messages().sendItemContainerFull(-1, 42, 516, player.inventory())
                            player.messages().sendClientScript(917, -1, -2)
                           // player.messages().sendOpenSub(548, 23, 12, 0)
                            player.messages().sendOpenSub(548, 67, 15, 0)
                            player.messages().sendAccessMask(12, 12, 0, 815, WidgetEvent.IF_BUTTON1)
                            player.messages().sendAccessMask(12, 12, 825, 833, WidgetEvent.IF_BUTTON1, WidgetEvent.IF_BUTTON2)
                            player.messages().sendAccessMask(12, 12, 834, 843, WidgetEvent.IF_BUTTON1)
                            player.messages().sendAccessMask(12, 10, 10, 10, WidgetEvent.IF_BUTTON1)
                            player.messages().sendAccessMask(12, 10, 11, 19, WidgetEvent.IF_BUTTON1)
                            player.messages().sendAccessMask(15, 3, 0, 27, 1181694)
                            player.messages().sendAccessMask(15, 12, 0, 27, 1054)
                            player.messages().sendAccessMask(15, 4, 0, 27, WidgetEvent.IF_BUTTON1)
                            player.messages().sendAccessMask(12, 46, 1, 816, WidgetEvent.IF_BUTTON1)
                            player.messages().sendAccessMask(12, 49, 0, 3, WidgetEvent.IF_BUTTON1)
                            player.messages().sendClientScript(
                                1495,
                                "Members' capacity: 800<br>+8 for your Authenticator<br>Set a PIN for 8 more.",
                                786439,
                                786546
                            )

                            /*player.widgetViewport {
                                widgetId = 12
                                //extensionWidgetId = 15
                                setup {

                                }
                            }*/

                            /*runscript: 917
                            [917, -1, -2]
                            channel.write(IfOpenSubMessage(548, 23, 12, 0))
                            channel.write(IfOpenSubMessage(548, 67, 15, 0))
                            messages().sendAccessMask(12, 12, 0, 815, 1312766)
                            messages().sendAccessMask(12, 12, 825, 833, 2)
                            messages().sendAccessMask(12, 12, 834, 843, 1048576)
                            messages().sendAccessMask(12, 10, 10, 10, 1048706)
                            messages().sendAccessMask(12, 10, 11, 19, 1179842)
                            messages().sendAccessMask(15, 3, 0, 27, 1181694)
                            messages().sendAccessMask(15, 12, 0, 27, 1054)
                            messages().sendAccessMask(15, 4, 0, 27, 1180674)
                            messages().sendAccessMask(12, 46, 1, 816, 2)
                            messages().sendAccessMask(12, 49, 0, 3, 2)
                            runscript: 1495
                            [1495, Members' capacity: 800<br>+8 for your Authenticator<br>Set a PIN for 8 more., 786439, 786546]*/

                        } else {
                            player.widgetViewport {
                                widgetId = 553
                                setup {
                                    player.messages().sendClientScript(1104, 1, 1)
                                }
                            }
                        }

                    }
                }
            }
        }
        assert(WidgetRegistration.assertWidgetIsRegistered(162))
    }

    override fun stop() {
        WidgetRegistration.deregisterWidget(162)
    }
}