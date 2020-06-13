package rs.emulator.entity.actor.player.storage.container

import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

data class ContainerEvent<T : Item>(val item : T, var cancelled : Boolean = false)