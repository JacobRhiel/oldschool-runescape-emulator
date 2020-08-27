package rs.emulator.plugin

import org.pf4j.Plugin
import org.pf4j.PluginWrapper
import rs.emulator.Repository
import rs.emulator.definitions.enums.EnumDefinition
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.messages.chat.ChatMessageType
import rs.emulator.entity.actor.player.widgets.WidgetViewport
import rs.emulator.widget.WidgetRegistration

class LobbyPlugin(wrapper: PluginWrapper) : Plugin(wrapper) {

    override fun start() {
        super.start()
        WidgetRegistration.registerWidget(378, LobbyPlugin::class.java.simpleName) {
            components {
                addActionComponent(78) {
                    val enum548 = Repository.getDefinition<EnumDefinition>(1129)
                    val enum161 = Repository.getDefinition<EnumDefinition>(1132)
                    if(it.option == 14) {
                        val player = it.source
                        val msgs = player.messages()
                        player.widgetViewport.close(WidgetViewport.OverlayFrame.VIEW_PORT)
                        player.widgetViewport.openOverlay(WidgetViewport.OverlayMode.FIXED)
                        msgs.sendLargeVarp(1042, 9888)
                        msgs.sendLargeVarp(1042, 9920)
                        msgs.sendLargeVarp(1042, 9984)
                        msgs.sendLargeVarp(1042, 10016)
                        msgs.sendLargeVarp(1055, -2147473856)
                        msgs.sendLargeVarp(1042, 10176)
                        moveSubsToFixedGameFrame(player, enum161, enum548)
                        msgs.sendClientScript(
                            2014,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0
                        )
                        msgs.sendClientScript(
                            2015,
                            0
                        )
                        player.widgetViewport.open(149, WidgetViewport.OverlayFrame.TABS)
                        player.widgetViewport.open(162, WidgetViewport.OverlayFrame.COMMUNICATION_HUB)
                        player.widgetViewport.open(160, WidgetViewport.OverlayFrame.MINIMAP)
                        msgs.sendChatMessage("Welcome to Grinderscape.", ChatMessageType.WELCOME)
                    }
                }
            }
        }

    }

    override fun stop() {
        super.stop()
    }

    private fun moveSubsToFixedGameFrame(player: IPlayer, enum: EnumDefinition, other: EnumDefinition) {
        enum.intValues.forEachIndexed { index, it ->
            val hash = enum.keys[index]
            val index548 = other.keys.indexOfFirst { it == hash }
            val hash548 = other.intValues[index548]
            if (hash548 != -1) {
                player.messages().sendMoveSub(
                    hash548 shr 16,
                    hash548 and 255,
                    it shr 16,
                    it and 255
                )
            }
        }
    }
}