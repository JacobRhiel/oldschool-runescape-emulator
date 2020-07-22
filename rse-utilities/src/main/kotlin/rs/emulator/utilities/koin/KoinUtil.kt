package rs.emulator.utilities.koin

import org.koin.core.context.KoinContextHandler

/**
 *
 * @author Chk
 */
inline fun <reified T : Any> get() = KoinContextHandler.get().get<T>()