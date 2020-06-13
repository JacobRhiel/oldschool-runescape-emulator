package rs.emulator.buffer.reader

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import rs.emulator.buffer.BufferUtils.readJagexString
import rs.emulator.buffer.BufferUtils.readString
import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import kotlin.math.pow

/**
 * A utility class for reading [ByteBuf]s.
 *
 * @author Graham
 * @author Chk
 */
class BufferedReader
{

    val buffer: ByteBuf

    val length: Int get() { return buffer.writableBytes() }

    val readableBytes: Int get() { return buffer.readableBytes() }

    constructor(size: Number) : this(Unpooled.buffer(size.toInt()))

    constructor(bytes: ByteArray) : this(Unpooled.wrappedBuffer(bytes))

    constructor(buffer: ByteBuf) {
        this.buffer = buffer
    }

    /**
     * Gets a string from the buffer.
     *
     * @return The string.
     * @throws IllegalStateException If this reader is not in byte access mode.
     */
    val string: String
        get() = buffer.readString()

    val jagString: String
        get() = buffer.readJagexString()

    val bigSmart: Int
        get()
        {
            val peek = buffer.getByte(buffer.readerIndex()).toInt()
            return if(peek >= 0) buffer.readUnsignedShort() and 0xFFFF
            else buffer.readInt() and Integer.MAX_VALUE
        }

    /**
     * Gets an unsigned smart from the buffer.
     *
     * @return The smart.
     * @throws IllegalStateException If this reader is not in byte access mode.
     */
    val unsignedSmart: Int
        get()
        {
            val peek = buffer.getByte(buffer.readerIndex()).toInt()
            return if (peek < 128) buffer.readByte().toInt()
            else buffer.readShort() - 0x8000
        }

    val unsignedIntSmartShortCompat: Int
        get()
        {
            var var1 = 0
            var var2: Int
            var2 = unsignedSmart
            while (var2 == 32767)
            {
                var1 += 32767
                var2 = unsignedSmart
            }
            var1 += var2
            return var1
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
    private operator fun get(type: DataType, order: DataOrder, transformation: DataTransformation) : Long
    {

        var longValue: Long = 0

        val length = type.bytes

        when(order)
        {

            DataOrder.BIG ->
                (length - 1).downTo(0).forEach {
                    longValue = if (it == 0 && transformation != DataTransformation.NONE)
                    {
                        when (transformation)
                        {
                            DataTransformation.ADD -> longValue or ((buffer.readByte().toLong() - 128 and 0xFFL))
                            DataTransformation.NEGATE -> longValue or (-buffer.readByte().toLong() and 0xFFL)
                            DataTransformation.SUBTRACT -> longValue or ((128 - buffer.readByte().toLong() and 0xFFL))
                            else -> throw IllegalArgumentException("Unknown transformation.")
                        }
                    } else
                        longValue or (buffer.readByte().toLong() and 0xFFL shl it * 8)
                }

            DataOrder.LITTLE ->
                0.until(length).forEach {
                    longValue = if (it == 0 && transformation != DataTransformation.NONE)
                        when (transformation)
                        {
                            DataTransformation.ADD -> longValue or (buffer.readByte().toLong() - 128 and 0xFF)
                            DataTransformation.NEGATE -> longValue or (-buffer.readByte().toLong() and 0xFF)
                            DataTransformation.SUBTRACT -> longValue or (128 - buffer.readByte().toLong() and 0xFF)
                            else -> throw IllegalArgumentException("Unknown transformation.")
                        }
                    else
                        longValue or (buffer.readByte().toLong() and 0xFFL shl it * 8)
                }

            DataOrder.MIDDLE ->
            {

                if (transformation != DataTransformation.NONE)
                    throw IllegalArgumentException("Middle endian cannot be transformed.")

                if (type != DataType.INT)
                    throw IllegalArgumentException("Middle endian can only be used with an integer.")

                longValue = longValue or (buffer.readByte().toInt() and 0xFF shl 8).toLong()
                longValue = longValue or (buffer.readByte().toInt() and 0xFF).toLong()
                longValue = longValue or (buffer.readByte().toInt() and 0xFF shl 24).toLong()
                longValue = longValue or (buffer.readByte().toInt() and 0xFF shl 16).toLong()

            }

            DataOrder.INVERSED_MIDDLE ->
            {

                if (transformation != DataTransformation.NONE)
                    throw IllegalArgumentException("Inversed middle endian cannot be transformed.")

                if (type != DataType.INT)
                    throw IllegalArgumentException("Inversed middle endian can only be used with an integer.")

                longValue = longValue or (buffer.readByte().toInt() and 0xFF shl 16).toLong()
                longValue = longValue or (buffer.readByte().toInt() and 0xFF shl 24).toLong()
                longValue = longValue or (buffer.readByte().toInt() and 0xFF).toLong()
                longValue = longValue or (buffer.readByte().toInt() and 0xFF shl 8).toLong()

            }

        }
        return longValue

    }

    /**
     * Gets a signed data type from the buffer with the specified order and transformation.
     *
     * @param type The data type.
     * @param order The byte order.
     * @param transformation The data transformation.
     * @return The value.
     * @throws IllegalStateException If this reader is not in byte access mode.
     * @throws IllegalArgumentException If the combination is invalid.
     */
    @JvmOverloads
    fun getSigned(
        type: DataType,
        order: DataOrder = DataOrder.BIG,
        transformation: DataTransformation = DataTransformation.NONE
    ): Long
    {

        var longValue = get(type, order, transformation)

        if (type != DataType.LONG)
        {

            val max = (2.0.pow((type.bytes * 8 - 1).toDouble()) - 1).toInt()

            if (longValue > max)
                longValue -= ((max + 1) * 2).toLong()

        }
        return longValue
    }

    /**
     * Gets a signed data type from the buffer with the specified transformation.
     *
     * @param type The data type.
     * @param transformation The data transformation.
     * @return The value.
     * @throws IllegalStateException If this reader is not in byte access mode.
     * @throws IllegalArgumentException If the combination is invalid.
     */
    fun getSigned(
        type: DataType,
        transformation: DataTransformation
    ): Long = getSigned(type, DataOrder.BIG, transformation)

    /**
     * Gets an unsigned data type from the buffer with the specified order and transformation.
     *
     * @param type The data type.
     * @param order The byte order.
     * @param transformation The data transformation.
     * @return The value.
     * @throws IllegalStateException If this reader is not in byte access mode.
     * @throws IllegalArgumentException If the combination is invalid.
     */
    @JvmOverloads
    fun getUnsigned(
        type: DataType,
        order: DataOrder = DataOrder.BIG,
        transformation: DataTransformation = DataTransformation.NONE
    ): Long
    {

        val longValue = get(type, order, transformation)

        check(type != DataType.LONG) { "Longs must be read as a signed type." }

        return longValue and -0x1L

    }

    /**
     * Gets an unsigned data type from the buffer with the specified transformation.
     *
     * @param type The data type.
     * @param transformation The data transformation.
     * @return The value.
     * @throws IllegalStateException If this reader is not in byte access mode.
     * @throws IllegalArgumentException If the combination is invalid.
     */
    fun getUnsigned(
        type: DataType,
        transformation: DataTransformation
    ): Long = getUnsigned(type, DataOrder.BIG, transformation)

    /**
     * Gets bytes.
     *
     * @param bytes The target byte array.
     * @throws IllegalStateException If this reader is not in byte access mode.
     */
    fun getBytes(bytes: ByteArray) = bytes.indices.forEach { bytes[it] = buffer.readByte() }

    /**
     * Gets bytes with the specified transformation.
     *
     * @param transformation The transformation.
     * @param bytes The target byte array.
     * @throws IllegalStateException If this reader is not in byte access mode.
     */
    fun getBytes(
        transformation: DataTransformation,
        bytes: ByteArray
    )
    {

        if (transformation == DataTransformation.NONE)
            getBytesReverse(bytes)
        else
            bytes.indices.forEach { bytes[it] = getSigned(DataType.BYTE, transformation).toByte() }

    }

    /**
     * Gets bytes in reverse with the specified transformation.
     *
     * @param transformation The transformation.
     * @param bytes The target byte array.
     * @throws IllegalStateException If this reader is not in byte access mode.
     */
    fun getBytesReverse(
        transformation: DataTransformation,
        bytes: ByteArray
    )
    {

        if (transformation == DataTransformation.NONE)
            getBytesReverse(bytes)
        else
            bytes.indices.reversed().forEach { bytes[it] = getSigned(DataType.BYTE, transformation).toByte() }

    }

    /**
     * Gets bytes in reverse.
     *
     * @param bytes The target byte array.
     * @throws IllegalStateException If this reader is not in byte access mode.
     */
    fun getBytesReverse(bytes: ByteArray) = bytes.indices.reversed().forEach { bytes[it] = buffer.readByte() }

    /**
     * Copies the current buffer and returns the reversed copy of it.
     * @return The reversed buffer as a [ByteArray].
     */
    fun reverse() = this.buffer.copy().array().reversedArray()

    fun readBytes(byte: ByteArray) = buffer.readBytes(byte)

    fun readBytes(bytes: ByteArray, offset: Int, length: Int) = buffer.readBytes(bytes, offset, length)

    fun readerIndex() = this.buffer.readerIndex()

    fun markReaderIndex(): ByteBuf = this.buffer.markReaderIndex()

    fun markReaderIndex(index: Int): ByteBuf = this.buffer.readerIndex(index)

    fun resetReaderIndex(): ByteBuf = this.buffer.resetReaderIndex()

    fun skipBytes(length: Int) = this.buffer.skipBytes(length)

    /**
     * Resets the buffer.
     */
    fun reset() = this.buffer.clear()

    fun toArray() : ByteArray = this.buffer.array()

    fun copy() = BufferedReader(this.buffer.copy())

}