package rs.emulator.entity.widgets

import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

class WidgetViewport(player: IPlayer) {

    val visibleWidgets = mutableMapOf<Int, Widget>()

    operator fun get(widgetId: Int): Widget {
        return visibleWidgets[widgetId] ?: throw Error("No Widget found for $widgetId.")
    }

    operator fun set(widgetId: Int, widget: Widget) {
        visibleWidgets[widgetId] = widget
    }

}