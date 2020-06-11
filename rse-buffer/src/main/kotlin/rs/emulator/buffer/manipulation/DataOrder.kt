package rs.emulator.buffer.manipulation

/**
 * Represents the order of bytes in a [DataType] when [DataType.getBytes] `> 1`.
 *
 * @author Graham
 */
enum class DataOrder
{

    /**
     * A most significant byte to the least significant byte.
     */
    BIG,

    /**
     * Also known as the V2 order.
     */
    INVERSED_MIDDLE,

    /**
     * The least significant byte to a most significant byte.
     */
    LITTLE,

    /**
     * Also known as the V1 order.
     */
    MIDDLE

}