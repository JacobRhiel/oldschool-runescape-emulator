package rs.emulator.entity.material

import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

abstract class AbstractMaterialFactory<T : Item> {

    abstract fun create(id : Int, amount : Int = 1, stackable : Boolean = false) : T
    abstract fun create(block : T.() -> Unit) : T

}