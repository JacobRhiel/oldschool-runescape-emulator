package rs.emulator.entity.material.items

/**
 *
 * @author javatar
 */

abstract class IntractableItem(id: Int) : Item(id) {

    protected abstract fun interactActual()

    fun interact(predicate : () -> Boolean) {
        if(predicate()) {
            interactActual()
        }
    }

}