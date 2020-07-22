package rs.emulator.entity.widgets

import rs.emulator.entity.widgets.widgets.FixedGameFrameWidget
import rs.emulator.entity.widgets.widgets.LegacyResizableGameFrameWidget
import rs.emulator.entity.widgets.widgets.ResizableGameFrameWidget

/**
 *
 * @author javatar
 */

class WidgetViewport {

    val visibleWidgets = mutableMapOf<Int, Widget>()

    var gameframeMode = GameFrameMode.FIXED
    var rootWidget: Widget = FixedGameFrameWidget()
        private set(value) {
            field = value
        }

    fun switchMode(mode: GameFrameMode) {
        if (gameframeMode != mode) {
            gameframeMode = mode
            rootWidget.dispose()
            rootWidget = when (mode) {
                GameFrameMode.FIXED -> FixedGameFrameWidget()
                GameFrameMode.RESIZABLE -> ResizableGameFrameWidget()
                GameFrameMode.LEGACY_RESIZABLE -> LegacyResizableGameFrameWidget()
            }
        }
    }

    fun openOnPlayerViewport(component: Component): Component {
        return when (gameframeMode) {
            GameFrameMode.FIXED -> rootWidget[23].open(component)
            GameFrameMode.RESIZABLE -> rootWidget[15].open(component)
            GameFrameMode.LEGACY_RESIZABLE -> rootWidget[15].open(component)
        }
    }

    fun openTab(component: Component) {

    }

    fun openOnCommutationHub(component: Component) {

    }

    operator fun get(widgetId: Int): Widget {
        return visibleWidgets[widgetId] ?: throw Error("No Widget found for $widgetId.")
    }

    operator fun set(widgetId: Int, widget: Widget) {
        if (!visibleWidgets.containsKey(widgetId)) {
            visibleWidgets[widgetId] = widget
        }
    }

    enum class GameFrameMode {
        FIXED,
        RESIZABLE,
        LEGACY_RESIZABLE
    }

}