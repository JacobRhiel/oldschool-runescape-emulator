package rs.emulator.entity.widget.display

import rs.emulator.entity.widget.Widget
import rs.emulator.entity.widgets.IWidgetDisplay

/**
 *
 * @author Chk
 */
class WidgetDisplay(override val widgets: MutableMap<Int, Widget>) : IWidgetDisplay<Widget>
{

}