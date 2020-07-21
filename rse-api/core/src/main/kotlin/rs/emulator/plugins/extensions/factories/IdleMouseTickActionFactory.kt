package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.idle.IdleMouseTickAction

/**
 *
 * @author javatar
 */

interface IdleMouseTickActionFactory : ExtensionPoint {

    fun registerIdleMouseTickAction(
        ticks: Long
    ): IdleMouseTickAction

}