package rs.emulator.entity.widgets

import io.reactivex.disposables.Disposable
import rs.emulator.entity.widgets.widgets.FixedGameFrameWidget
import rs.emulator.entity.widgets.widgets.LegacyResizableGameFrameWidget
import rs.emulator.entity.widgets.widgets.ResizableGameFrameWidget

/**
 *
 * @author javatar
 */

class WidgetViewport {

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
        return getDynamicComponent(Frames.VIEW_PORT).open(component)
    }

    fun openTab(component: Component): Component {
        return getDynamicComponent(Frames.TABS).open(component)
    }

    fun openOnCommutationHub(component: Component): Component {
        return getDynamicComponent(Frames.COMMUNICATION_HUB).open(component)
    }

    inline fun <reified E : ComponentEvent> subscribeTo(frame: Frames, noinline onNext: (E) -> Unit): Disposable {
        return getDynamicComponent(frame).subscribe(onNext)
    }

    fun getDynamicComponent(frame: Frames): DynamicComponent {
        return when (frame) {
            Frames.VIEW_PORT -> when (gameframeMode) {
                GameFrameMode.FIXED -> rootWidget[GameFrameMode.FIXED.viewportId]
                GameFrameMode.RESIZABLE -> rootWidget[GameFrameMode.RESIZABLE.viewportId]
                GameFrameMode.LEGACY_RESIZABLE -> rootWidget[GameFrameMode.LEGACY_RESIZABLE.viewportId]
            }
            Frames.COMMUNICATION_HUB -> when (gameframeMode) {
                GameFrameMode.FIXED -> rootWidget[GameFrameMode.FIXED.communicationHuhId]
                GameFrameMode.RESIZABLE -> rootWidget[GameFrameMode.RESIZABLE.communicationHuhId]
                GameFrameMode.LEGACY_RESIZABLE -> rootWidget[GameFrameMode.LEGACY_RESIZABLE.communicationHuhId]
            }
            Frames.TABS -> when (gameframeMode) {
                GameFrameMode.FIXED -> rootWidget[GameFrameMode.FIXED.tabsId]
                GameFrameMode.RESIZABLE -> rootWidget[GameFrameMode.RESIZABLE.tabsId]
                GameFrameMode.LEGACY_RESIZABLE -> rootWidget[GameFrameMode.LEGACY_RESIZABLE.tabsId]
            }
        }
    }

    enum class GameFrameMode(val viewportId: Int, val tabsId: Int, val communicationHuhId: Int) {
        FIXED(23, -1, 27),
        RESIZABLE(15, -1, 32),
        LEGACY_RESIZABLE(15, -1, 34)
    }

    enum class Frames {

        VIEW_PORT,
        COMMUNICATION_HUB,
        TABS

    }

}