package rs.emulator.collections

/**
 *
 * @author javatar
 */

fun <T> MutableList<T>.applyEach(block : T.() -> Unit) {
    this.forEach { block(it) }
}