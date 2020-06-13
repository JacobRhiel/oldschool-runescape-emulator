package rs.emulator.utilities.caffeine

import com.github.benmanes.caffeine.cache.Cache

/**
 *
 * @author Chk
 */
fun <K : Any, V> Cache<K, V>.getOrPut(key: K, defaultValue: () -> V): V {
    val value = getIfPresent(key)
    return if (value == null) {
        val answer = defaultValue()
        put(key, answer)
        answer
    } else {
        value
    }
}