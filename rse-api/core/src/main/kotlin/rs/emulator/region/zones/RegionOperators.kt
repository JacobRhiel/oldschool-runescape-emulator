package rs.emulator.region.zones

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import rs.emulator.entity.IEntity

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
fun <E : IEntity> MutableStateFlow<RegionZone>.sendEvent(event: RegionZoneEvent<E>) {
    value.sendEvent(event)
}

@ExperimentalCoroutinesApi
inline fun <reified E> Flow<RegionZoneEvent<*>>.repeatEvent(period: Long, crossinline predicate: (E) -> Boolean) = flow {
    collect {
        if(it is E) {
            while(!predicate(it)) {
                delay(period)
            }
        } else {
            emit(it)
        }
    }
}

@ExperimentalCoroutinesApi
inline fun <reified E : RegionZoneEvent<*>> Flow<RegionZoneEvent<*>>.onEachEvent(crossinline onEach : FlowCollector<RegionZoneEvent<*>>.(E) -> Unit) : Flow<RegionZoneEvent<*>> = flow {
    this@onEachEvent.collect {
        if(it is E) {
            onEach(it)
        }
        emit(it)
    }
}
@ExperimentalCoroutinesApi
inline fun <reified E : RegionZoneEvent<*>> Flow<RegionZoneEvent<*>>.consumeEachEvent(crossinline onEach : FlowCollector<RegionZoneEvent<*>>.(E) -> Unit) : Flow<RegionZoneEvent<*>> = flow {
    this@consumeEachEvent.collect {
        if(it is E) {
            onEach(it)
        } else {
            emit(it)
        }
    }
}