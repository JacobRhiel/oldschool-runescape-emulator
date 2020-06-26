package rs.emulator.network.packet

import com.google.common.base.Preconditions
import rs.emulator.buffer.BufferUtils.readJagexString
import rs.emulator.buffer.BufferUtils.readString
import rs.emulator.buffer.DataConstants
import rs.emulator.buffer.access.AccessMode
import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.network.packet.message.GamePacketMessage

/**
 * A utility class for reading [GamePacketMessage]s.
 *
 * @author Graham
 * @author Chk
 */
class GamePacketReader(packet: GamePacketMessage)
    : BufferedReader(packet.payload)
{

    /**
     * The current bit index.
     */
    private var bitIndex: Int = 0

    /**
     * The current mode.
     */
    private var mode = AccessMode.BYTE_ACCESS

    /**
     * Gets a bit from the buffer.
     *
     * @return The value.
     * @throws IllegalStateException If the reader is not in bit access mode.
     */
    val bit: Int
        get() = getBits(1)

    /**
     * Checks that this reader is in the bit access mode.
     *
     * @throws IllegalStateException If the reader is not in bit access mode.
     */
    private fun checkBitAccess() = Preconditions.checkState(mode === AccessMode.BIT_ACCESS,
            "For bit-based calls to work, the mode must be bit access.")

    /**
     * Checks that this reader is in the byte access mode.
     *
     * @throws IllegalStateException If the reader is not in byte access mode.
     */
    private fun checkByteAccess() = Preconditions.checkState(mode === AccessMode.BYTE_ACCESS,
            "For byte-based calls to work, the mode must be byte access.")

    /**
     * Gets the length of this reader.
     *
     * @return The length of this reader.
     */
    override val length: Int
        get() {
            checkByteAccess()
            return buffer.writableBytes()
        }

    override val readableBytes: Int
        get() {
            checkByteAccess()
            return buffer.readableBytes()
        }


    /**
     * Gets a signed smart from the buffer.
     *
     * @return The smart.
     * @throws IllegalStateException If this reader is not in byte access mode.
     */
    val signedSmart: Int
        get() {
            checkByteAccess()
            val peek = buffer.getByte(buffer.readerIndex()).toInt()
            return if (peek < 128) {
                buffer.readByte() - 64
            } else buffer.readShort() - 49152
        }

    /**
     * Gets a string from the buffer.
     *
     * @return The string.
     * @throws IllegalStateException If this reader is not in byte access mode.
     */
    override val string: String
        get() {
            checkByteAccess()
            return buffer.readString()
        }

    override val jagString: String
        get() {
            checkByteAccess()
            return buffer.readJagexString()
        }

    /**
     * Gets an unsigned smart from the buffer.
     *
     * @return The smart.
     * @throws IllegalStateException If this reader is not in byte access mode.
     */
    override val unsignedSmart: Int
        get() {
            checkByteAccess()
            val peek = buffer.getByte(buffer.readerIndex()).toInt()
            return if (peek < 128) {
                buffer.readByte().toInt()
            } else buffer.readShort() - 32768
        }

    /**
     * Reads a standard data type from the buffer with the specified order and transformation.
     *
     * @param type The data type.
     * @param order The data order.
     * @param transformation The data transformation.
     * @return The value.
     * @throws IllegalStateException If this reader is not in byte access mode.
     * @throws IllegalArgumentException If the combination is invalid.
     */
    override operator fun get(type: DataType, order: DataOrder, transformation: DataTransformation): Long
    {
        checkByteAccess()
        return super.get(type, order, transformation)
    }

    /**
     * Gets the specified amount of bits from the buffer.
     *
     * @param amount The amount of bits.
     * @return The value.
     * @throws IllegalStateException If the reader is not in bit access mode.
     * @throws IllegalArgumentException If the number of bits is not between 1 and 31 inclusive.
     */
    fun getBits(amount: Int): Int
    {

        var amountOfBits = amount

        Preconditions.checkArgument(amountOfBits in 0..32, "Number of bits must be between 1 and 32 inclusive.")

        checkBitAccess()

        var bytePos = bitIndex shr 3
        var bitOffset = 8 - (bitIndex and 7)
        var value = 0

        bitIndex += amountOfBits

        while (amountOfBits > bitOffset)
        {
            value += buffer.getByte(bytePos++).toInt() and DataConstants.BIT_MASK[bitOffset] shl amountOfBits - bitOffset
            amountOfBits -= bitOffset
            bitOffset = 8
        }

        value += if (amountOfBits == bitOffset)
            buffer.getByte(bytePos).toInt() and DataConstants.BIT_MASK[bitOffset]
        else
            buffer.getByte(bytePos).toInt() shr bitOffset - amountOfBits and DataConstants.BIT_MASK[amountOfBits]

        return value
    }

    /**
     * Gets bytes.
     *
     * @param bytes The target byte array.
     * @throws IllegalStateException If this reader is not in byte access mode.
     */
    override fun getBytes(bytes: ByteArray)
    {

        checkByteAccess()

        super.getBytes(bytes)

    }

    /**
     * Gets bytes in reverse.
     *
     * @param bytes The target byte array.
     * @throws IllegalStateException If this reader is not in byte access mode.
     */
    override fun getBytesReverse(bytes: ByteArray)
    {

        checkByteAccess()

        getBytesReverse(bytes)

    }

    /**
     * Switches this builder's mode to the bit access mode.
     *
     * @throws IllegalStateException If the builder is already in bit access mode.
     */
    fun switchToBitAccess()
    {

        Preconditions.checkState(mode !== AccessMode.BIT_ACCESS, "Already in bit access mode.")

        mode = AccessMode.BIT_ACCESS

        bitIndex = buffer.readerIndex() * 8

    }

    /**
     * Switches this builder's mode to the byte access mode.
     *
     * @throws IllegalStateException If the builder is already in byte access mode.
     */
    fun switchToByteAccess()
    {

        Preconditions.checkState(mode !== AccessMode.BYTE_ACCESS, "Already in byte access mode.")

        mode = AccessMode.BYTE_ACCESS

        buffer.readerIndex((bitIndex + 7) / 8)

    }

}