package rs.emulator.widgets

import rs.emulator.widgets.components.Component
import rs.emulator.widgets.components.ContainerComponent
import rs.emulator.widgets.components.OverlayComponent
import rs.emulator.widgets.components.Widget
import rs.emulator.widgets.events.ComponentClickEvent
import rs.emulator.widgets.events.ComponentCloseEvent
import rs.emulator.widgets.events.ComponentOpenEvent
import kotlin.properties.Delegates

/**
 *
 * @author javatar
 */

class WidgetViewport {

    var overlayMode: OverlayMode by Delegates.observable(OverlayMode.FIXED) { _, old, new ->
        if (old !== new) {
            overlay.dispose()
            overlay = when (new) {
                OverlayMode.FIXED -> OverlayWidgets.FIXED_GAME_FRAME.overlay
                OverlayMode.RESIZABLE -> OverlayWidgets.RESIZABLE_GAME_FRAME.overlay
                OverlayMode.LEGACY_RESIZABLE -> OverlayWidgets.LEGACY_RESIZABLE_GAME_FRAME.overlay
            }
        }
    }

    private var overlay: OverlayComponent = OverlayWidgets.FIXED_GAME_FRAME.overlay

    fun fireClickEvent(interfaceId: Int, component: Int, slot: Int, item: Int) {
        val widget = Widget(interfaceId)
        val comp = Component(component)
        when {
            getContainerComponent(Frames.VIEW_PORT).isActive(widget) -> {
                val con = getContainerComponent(Frames.VIEW_PORT)[widget]
                if (con.isActive(comp)) {
                    con[comp].apply { this.events.onNext(ComponentClickEvent(widget, this, slot, item)) }
                }
            }
            getContainerComponent(Frames.COMMUNICATION_HUB).isActive(widget) -> {
                val con = getContainerComponent(Frames.COMMUNICATION_HUB)[widget]
                if (con.isActive(comp)) {
                    con[comp].apply { this.events.onNext(ComponentClickEvent(widget, this, slot, item)) }
                }
            }
            getContainerComponent(Frames.TABS).isActive(widget) -> {
                val con = getContainerComponent(Frames.TABS)[widget]
                if (con.isActive(comp)) {
                    con[comp].apply { this.events.onNext(ComponentClickEvent(widget, this, slot, item)) }
                }
            }
        }
    }

    fun open(frame: Frames, widget: Widget, block: Widget.() -> Unit): Widget {
        val container = getContainerComponent(frame)
        val comp = container[widget]
        container.events.onNext(ComponentOpenEvent(overlay.id, container.id, widget.id))
        if (comp.active) {
            return comp.also { it.block() }
        }
        return widget.also { it.active = true; it.block() }
    }

    fun close(frame: Frames, widget: Widget) {
        close(getContainerComponent(frame), widget)
    }

    private fun close(container: ContainerComponent<Widget>, widget: Widget) {
        val comp = container[widget]
        if (comp.active) {
            val closeEvent = ComponentCloseEvent(comp)
            comp.events.onNext(ComponentCloseEvent(comp))
            if (!closeEvent.canceled) {
                comp.active = false
                comp.close()
            }
        }
    }

    fun closeAll(frame: Frames) {
        getContainerComponent(frame).apply {
            this.components.values.forEach {
                close(this, it)
            }
        }
    }

    fun getContainerComponent(frame: Frames): ContainerComponent<Widget> {
        return when (frame) {
            Frames.VIEW_PORT -> when (overlayMode) {
                OverlayMode.FIXED -> overlay[ContainerComponent(OverlayMode.FIXED.viewportId)]
                OverlayMode.RESIZABLE -> overlay[ContainerComponent(OverlayMode.RESIZABLE.viewportId)]
                OverlayMode.LEGACY_RESIZABLE -> overlay[ContainerComponent(OverlayMode.LEGACY_RESIZABLE.viewportId)]
            }
            Frames.COMMUNICATION_HUB -> when (overlayMode) {
                OverlayMode.FIXED -> overlay[ContainerComponent(OverlayMode.FIXED.communicationHuhId)]
                OverlayMode.RESIZABLE -> overlay[ContainerComponent(OverlayMode.RESIZABLE.communicationHuhId)]
                OverlayMode.LEGACY_RESIZABLE -> overlay[ContainerComponent(OverlayMode.LEGACY_RESIZABLE.communicationHuhId)]
            }
            Frames.TABS -> when (overlayMode) {
                OverlayMode.FIXED -> overlay[ContainerComponent(OverlayMode.FIXED.tabsId)]
                OverlayMode.RESIZABLE -> overlay[ContainerComponent(OverlayMode.RESIZABLE.tabsId)]
                OverlayMode.LEGACY_RESIZABLE -> overlay[ContainerComponent(OverlayMode.LEGACY_RESIZABLE.tabsId)]
            }
        }
    }

    enum class OverlayMode(val id: Int, val viewportId: Int, val tabsId: Int, val communicationHuhId: Int) {
        FIXED(548, 23, -1, 27),
        RESIZABLE(161, 15, -1, 32),
        LEGACY_RESIZABLE(164, 15, -1, 34)
    }

    enum class Frames {

        VIEW_PORT,
        COMMUNICATION_HUB,
        TABS

    }

}