package rs.emulator.entity.actor.player.storage

/**
 *
 * @author javatar
 */

interface IItemContainerManager<T : ItemContainer<*>> {

    fun register(key : Int, container : T, block : T.() -> Unit = {})

    fun container(key : Int) : T

}