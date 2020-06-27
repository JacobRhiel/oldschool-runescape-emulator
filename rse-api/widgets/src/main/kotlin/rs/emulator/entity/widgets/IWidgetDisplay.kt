package rs.emulator.entity.widgets

/**
 *
 * @author javatar
 */

interface IWidgetDisplay<W : IWidget> {

    val widgets : MutableMap<Int, W>

}