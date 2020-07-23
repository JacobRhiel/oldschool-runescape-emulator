package rs.emulator.widgets

import rs.emulator.widgets.components.OverlayComponent

enum class OverlayWidgets(val overlay: OverlayComponent) {

    FIXED_GAME_FRAME(OverlayComponent(548)),
    RESIZABLE_GAME_FRAME(OverlayComponent(161)),
    LEGACY_RESIZABLE_GAME_FRAME(OverlayComponent(164));

}