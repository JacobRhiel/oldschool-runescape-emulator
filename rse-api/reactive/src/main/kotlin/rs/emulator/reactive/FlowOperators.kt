package rs.emulator.reactive

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

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