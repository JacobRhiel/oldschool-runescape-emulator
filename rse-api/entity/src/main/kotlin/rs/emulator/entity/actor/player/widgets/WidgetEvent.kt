package rs.emulator.entity.actor.player.widgets

/**
 *
 * @author Chk
 */
enum class WidgetEvent(val mask: Int)
{

    PAUSEBUTTON(1 shl 0),
    IF_BUTTON1(1 shl 1),
    IF_BUTTON2(1 shl 2),
    IF_BUTTON3(1 shl 3),
    IF_BUTTON4(1 shl 4),
    IF_BUTTON5(1 shl 5),
    IF_BUTTON6(1 shl 6),
    IF_BUTTON7(1 shl 7),
    IF_BUTTON8(1 shl 8),
    IF_BUTTON9(1 shl 9),
    IF_BUTTON10(1 shl 10),
    OPOBJT(1 shl 11),
    OPNPCT(1 shl 12),
    OPLOCT(1 shl 13),
    OPPLAYERT(1 shl 14),
    OPHELDT(1 shl 15),
    IF_BUTTONT(1 shl 16),
    DRAG_DEPTH1(1 shl 17),
    DRAG_DEPTH2(2 shl 17),
    DRAG_DEPTH3(3 shl 17),
    DRAG_DEPTH4(4 shl 17),
    DRAG_DEPTH5(5 shl 17),
    DRAG_DEPTH6(6 shl 17),
    DRAG_DEPTH7(7 shl 17),
    DRAG_TARGET(1 shl 20),
    IF_TARGET(1 shl 21);

}