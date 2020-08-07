package rs.emulator.entity.actor.player.widgets

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.widgets.WidgetViewport.OverlayFrame.COMMUNICATION_HUB
import rs.emulator.entity.actor.player.widgets.WidgetViewport.OverlayFrame.VIEW_PORT
import java.util.*

/**
 *
 * @author javatar
 */

class WidgetViewport(val player: IPlayer) {

    var overlayMode = OverlayMode.FIXED

    val activeWidgets = EnumMap<OverlayFrame, Int>(OverlayFrame::class.java)

    fun isWidgetActive(widgetId: Int): Boolean {
        return widgetId == overlayMode.id || activeWidgets.values.any { it == widgetId }
    }

    fun open(widgetId: Int, frame: OverlayFrame) {
        close(frame)
        activeWidgets[frame] = widgetId
        when (frame) {
            VIEW_PORT -> player.messages().sendOpenSub(overlayMode.id, overlayMode.viewportId, widgetId, 0)
            COMMUNICATION_HUB -> player.messages()
                .sendOpenSub(overlayMode.id, overlayMode.communicationHuhId, widgetId, 0)
            //TABS -> player.messages().sendOpenSub(overlayMode.id, overlayMode.tabsId, widgetId, 0)
        }
    }

    fun close(frame: OverlayFrame) {
        activeWidgets[frame] = -1
    }

    enum class OverlayMode(val id: Int, val viewportId: Int, val tabsId: Int, val communicationHuhId: Int) {
        FIXED(548, 23, -1, 27),
        RESIZABLE(161, 15, -1, 32),
        LEGACY_RESIZABLE(164, 15, -1, 34)
    }

    enum class OverlayFrame {

        VIEW_PORT,
        COMMUNICATION_HUB,
        TABS

    }

}