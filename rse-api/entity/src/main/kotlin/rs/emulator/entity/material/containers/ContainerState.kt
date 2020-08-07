package rs.emulator.entity.material.containers

import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
class ContainerState(private val dirty: Boolean = false, val container: ItemContainer<*>) {
    fun invalidate() = ContainerState(!dirty, container)
}