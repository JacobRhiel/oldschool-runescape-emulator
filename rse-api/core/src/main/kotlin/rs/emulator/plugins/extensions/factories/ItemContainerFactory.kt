package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.storage.container.ItemContainer
import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

interface ItemContainerFactory<ITEM : Item> : ExtensionPoint {

    val containerKey : Int

    fun registerItemContainer(manager : IPlayer) : ItemContainer<ITEM>


}

class ContainerRegistrationException(key : Int) : Exception("Container key already registered")