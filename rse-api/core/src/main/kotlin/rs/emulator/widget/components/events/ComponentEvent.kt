package rs.emulator.widget.components.events

/**
 *
 * @author javatar
 */

interface ComponentEvent<S, C> {

    val source: S
    val component: C

}