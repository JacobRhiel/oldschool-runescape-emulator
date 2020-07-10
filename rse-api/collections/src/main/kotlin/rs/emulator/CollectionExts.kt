package rs.emulator

/**
 *
 * @author javatar
 */

fun <T> MutableList<T>.applyEach(block : T.() -> Unit) {
    this.forEach { block(it) }
}