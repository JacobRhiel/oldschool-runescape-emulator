package rs.emulator.entity.actor.player.widgets.dsl

import rs.emulator.entity.actor.player.widgets.WidgetViewport

@WidgetMetaContext
class WidgetContext(var widgetId: Int = -1) {

    var frame: WidgetViewport.OverlayFrame = WidgetViewport.OverlayFrame.VIEW_PORT
    var extensionWidgetId: Int = -1
    internal var setupContext: SetupContext = SetupContext { }
    internal var closeContext: CloseContext = CloseContext { }

    fun setup(setup: SetupContext.() -> Unit) {
        this.setupContext = SetupContext(setup)
    }

    fun close(close: CloseContext.() -> Unit) {
        this.closeContext = CloseContext(close)
    }

    companion object {
        val EMPTY_CONTEXT = WidgetContext()
    }

    @WidgetMetaContext
    class SetupContext(val setupBlock: SetupContext.() -> Unit) {
        fun setup() = setupBlock()
    }

    @WidgetMetaContext
    class CloseContext(val closeBlock: CloseContext.() -> Unit) {
        fun close() = closeBlock()
    }

}