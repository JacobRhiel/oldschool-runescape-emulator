package rs.emulator.plugin.widget

import rs.emulator.entity.widgets.DynamicComponent
import rs.emulator.entity.widgets.Widget

/**
 *
 * @author javatar
 */

class ChatWidget : Widget(162) {

    init {
        this[33] = DynamicComponent(33, this)
    }

}