package rs.emulator.definitions

/**
 *
 * @author javatar
 */

abstract class DefinitionFactory<T : Definition> {

    abstract fun provide(identifier : Int, block : T.() -> Unit = {}) : T

}