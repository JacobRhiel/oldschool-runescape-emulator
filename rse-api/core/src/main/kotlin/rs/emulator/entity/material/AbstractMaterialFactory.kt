package rs.emulator.entity.material

/**
 *
 * @author javatar
 */

interface AbstractMaterialFactory<T> {

    fun create(id: Int, amount: Int = 1, stackable: Boolean = false): T
    fun create(cache: Boolean = false, block: T.() -> Unit): T
    fun createFromMetaData(id: Int, amount: Int): T

}