package rs.emulator.entity.widgets

/**
 *
 * @author javatar
 */

class WidgetViewport {

    val visibleWidgets = mutableMapOf<Int, Widget>()

    operator fun get(widgetId: Int): Widget {
        return visibleWidgets[widgetId] ?: throw Error("No Widget found for $widgetId.")
    }

    operator fun set(widgetId: Int, widget: Widget) {
        if (!visibleWidgets.containsKey(widgetId)) {
            visibleWidgets[widgetId] = widget
        }
    }

}