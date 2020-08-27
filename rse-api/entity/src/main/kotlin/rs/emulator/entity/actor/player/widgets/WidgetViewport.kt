package rs.emulator.entity.actor.player.widgets

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.widgets.WidgetViewport.OverlayFrame.*
import rs.emulator.entity.actor.player.widgets.dsl.WidgetContext
import java.util.*

/**
 *
 * @author javatar
 */

class WidgetViewport(val player: IPlayer) {

    var overlayMode = OverlayMode.FULL_SCREEN

    val activeWidgets = EnumMap<OverlayFrame, Int>(OverlayFrame::class.java)
    val activeMetaData = mutableMapOf<OverlayFrame, WidgetContext>()

    init {
        for (value in values()) {
            activeMetaData[value] = WidgetContext.EMPTY_CONTEXT
        }
    }

    fun isWidgetActive(widgetId: Int): Boolean {
        return widgetId == overlayMode.id || activeWidgets.values.any { it == widgetId }
    }

    fun isWidgetActiveWithExtension(widgetId: Int, extensionId: Int): Boolean {
        return isWidgetActive(widgetId) && activeWidgets[VIEW_PORT_EXTENSION] == extensionId
    }

    fun openOverlay(overlayMode: OverlayMode) {
        this.overlayMode = overlayMode
        player.messages().sendOpenOverlay(overlayMode.id)
    }

    fun open(widgetId: Int, frame: OverlayFrame) {
        close(frame)
        activeMetaData[frame] = WidgetContext(widgetId).also { it.frame = frame }
        when (frame) {
            VIEW_PORT -> player.messages().sendOpenSub(overlayMode.id, overlayMode.viewportId, widgetId, 0)
            COMMUNICATION_HUB -> player.messages()
                .sendOpenSub(overlayMode.id, overlayMode.communicationHuhId, widgetId, 0)
            VIEW_PORT_EXTENSION -> player.messages().sendOpenSub(overlayMode.id, overlayMode.extensionId, widgetId, 0)
        }
    }

    fun close(frame: OverlayFrame) {
        activeMetaData[frame]?.closeContext?.close()
        activeMetaData[frame] = WidgetContext.EMPTY_CONTEXT
        activeWidgets[frame] = -1
        when (frame) {
            VIEW_PORT -> player.messages().sendCloseSub(overlayMode.id, overlayMode.viewportId)
            COMMUNICATION_HUB -> player.messages().sendCloseSub(overlayMode.id, overlayMode.communicationHuhId)
            VIEW_PORT_EXTENSION -> player.messages().sendCloseSub(overlayMode.id, overlayMode.extensionId)
        }
    }

    operator fun invoke(openBlock: WidgetContext.() -> Unit) {
        WidgetContext().also {
            it.openBlock()
            open(it.widgetId, it.frame)
            if(it.extensionWidgetId != -1) {
                open(it.extensionWidgetId, VIEW_PORT_EXTENSION)
            }
            it.setupContext.setup()
        }
    }

    enum class OverlayMode(val id: Int, val viewportId: Int, val extensionId: Int, val communicationHuhId: Int) {
        FIXED(548, 23, 67, 27),
        RESIZABLE(161, 15, -1, 32),
        LEGACY_RESIZABLE(164, 15, -1, 34),
        FULL_SCREEN(165, 29, -1, -1)
    }

    enum class OverlayFrame {

        /**
         * View port, this is used for any widget that appears within the player viewport.
         * eg, report abuse, bank etc
         */

        VIEW_PORT,

        /**
         * View port extension is the tab widget that is opened along side a widget
         * eg Opening bank would have the a tab widget open along side it.
         */
        VIEW_PORT_EXTENSION,

        /**
         * Communication hub represents everything related to social interaction
         * eg, public chat, private chat etc
         */
        COMMUNICATION_HUB,

        /**
         * Tabs, this overlay from is mainly used to validation on clicking
         * This does not send packets to the client, mainly used for validation.
         */
        TABS,

        /**
         * Minimap, this is used for click validation on orbs etc
         */
        MINIMAP

    }

}