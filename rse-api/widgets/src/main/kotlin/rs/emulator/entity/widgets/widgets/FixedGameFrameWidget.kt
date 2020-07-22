package rs.emulator.entity.widgets.widgets

import rs.emulator.entity.widgets.DynamicComponent
import rs.emulator.entity.widgets.Widget

/**
 *
 * @author javatar
 */

class FixedGameFrameWidget : Widget(548) {

    init {
        this[23] = DynamicComponent(23, this)
        this[27] = DynamicComponent(27, this)
    }

}