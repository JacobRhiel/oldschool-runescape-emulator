package rs.emulator.plugin

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.pf4j.Plugin
import org.pf4j.PluginWrapper
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
                    if(it.option == 78) {

                        if(player.username() == "hunter23912") {

                            player.widgetViewport {
                                widgetId = 12
                                extensionWidgetId = 15
                                setup {
                                    player.messages().sendClientScript(917, -1, -2)
                                    player.messages().sendAccessMask(12, 12, 0, 815, 1312766)
                                    player.messages().sendAccessMask(12, 12, 825, 833, 2)
                                    player.messages().sendAccessMask(12, 12, 834, 843, 1048576)
                                    player.messages().sendAccessMask(12, 10, 10, 10, 1048706)
                                    player.messages().sendAccessMask(12, 10, 11, 19, 1179842)
                                    player.messages().sendAccessMask(15, 3, 0, 27, 1181694)
                                    player.messages().sendAccessMask(15, 12, 0, 27, 1054)
                                    player.messages().sendAccessMask(15, 4, 0, 27, 1180674)
                                    player.messages().sendAccessMask(12, 46, 1, 816, 2)
                                    player.messages().sendAccessMask(12, 49, 0, 3, 2)
                                    player.messages().sendClientScript(
                                        1495,
                                        "Members' capacity: 800<br>+8 for your Authenticator<br>Set a PIN for 8 more.",
                                        786439,
                                        786546
                                    )
                                }
                            }

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