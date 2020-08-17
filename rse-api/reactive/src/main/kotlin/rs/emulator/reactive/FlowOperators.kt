package rs.emulator.reactive

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.runBlocking
import rs.emulator.utilities.contexts.scopes.ActorScope
import rs.emulator.utilities.koin.get

/**
 *
 * @author javatar
 */
inline fun <reified I> Flow<*>.onEachInstance(crossinline onEach: suspend (I) -> Unit) = flow {
    this@onEachInstance.collect {
        if(it is I) {
            onEach(it)
        } else {
            emit(it)
        }
    }
}

fun <T> Flow<T>.collectBlocking(action : (T) -> Unit) {
    runBlocking { collect { action(it) } }
}

fun <T> Flow<T>.launch() = launchIn(get<ActorScope>())

fun <T> intervalFlowOf(value: T, period: Long) = flow {
    while (true) {
        delay(period)
        emit(value)
    }
}

