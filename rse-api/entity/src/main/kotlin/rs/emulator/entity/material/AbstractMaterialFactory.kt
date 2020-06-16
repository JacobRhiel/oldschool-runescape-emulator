package rs.emulator.entity.material

/**
 *
 * @author javatar
 */

abstract class AbstractMaterialFactory<T> {

    abstract fun create(id : Int, amount : Int = 1, stackable : Boolean = false) : T
    abstract fun create(block : T.() -> Unit) : T

}