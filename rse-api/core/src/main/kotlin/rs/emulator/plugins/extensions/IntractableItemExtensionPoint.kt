package rs.emulator.plugins.extensions

import org.pf4j.ExtensionPoint
import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

abstract class IntractableItemExtensionPoint(val id: Int) : ExtensionPoint {

    protected abstract fun IPlayer.interactActual()

    fun IPlayer.interact(predicate : () -> Boolean) {
        if(predicate()) {
            interactActual()
        }
    }

}