package rs.emulator.buffer.writer

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import rs.emulator.buffer.manipulation.*
import rs.emulator.buffer.reader.BufferedReader

/**
 *
 * @author Chk
 */
open class BufferedWriter(protected val buffer: ByteBuf = Unpooled.buffer(0))
{

    /**
     * The current bit index.
     */
    private var bitIndex: Int = 0

    val byteBuf: ByteBuf = buffer

    val readableBytes: Int get() = buffer.readableBytes()

    /**
     * Gets the current length of the builder's buffer.
     *
     * @return The length of the buffer.
     */
    val length: Int
        get()
        {
            return buffer.writerIndex()
        }

    /**
     * Puts a standard data type with the specified value, byte order and transformation.
     *
     * @param type The data type.
     * @param order The byte order.
     * @param transformation The transformation.
     * @param value The value.
     * @throws IllegalArgumentException If the type, order, or transformation is unknown.
     */
    fun put(
            type: DataType,
            order: DataOrder,
            transformation: DataTransformation,
            value: Number
    )
    {

        check(type != DataType.SMART) { "Use `putSmart` instead." }

        val longValue = value.toLong()

        val length = type.bytes

        when (order)
        {

            DataOrder.BIG             -> for (i in length - 1 downTo 0)
            {
                if (i == 0 && transformation != DataTransformation.NONE)
                {
                    when (transformation)
                    {
                        DataTransformation.ADD      -> buffer.writeByte((longValue + 128).toByte().toInt())
                        DataTransformation.NEGATE   -> buffer.writeByte((-longValue).toByte().toInt())
                        DataTransformation.SUBTRACT -> buffer.writeByte((128 - longValue).toByte().toInt())
                        else                        -> throw IllegalArgumentException("Unknown transformation.")
                    }
                }
                else buffer.writeByte((longValue shr i * 8).toByte().toInt())
            }
            DataOrder.LITTLE          -> for (i in 0 until length)
            {
                if (i == 0 && transformation != DataTransformation.NONE)
                {
                    when (transformation)
                    {
                        DataTransformation.ADD      -> buffer.writeByte((longValue + 128).toByte().toInt())
                        DataTransformation.NEGATE   -> buffer.writeByte((-longValue).toByte().toInt())
                        DataTransformation.SUBTRACT -> buffer.writeByte((128 - longValue).toByte().toInt())
                        else                        -> throw IllegalArgumentException("Unknown transformation.")
                    }
                }
                else
                {
                    buffer.writeByte((longValue shr i * 8).toByte()
                                             .toInt())
                }
            }
            DataOrder.MIDDLE          ->
            {
                check(transformation == DataTransformation.NONE) { "Middle endian cannot be transformed." }
                check(type == DataType.INT) { "Middle endian can only be used with an integer." }
                buffer.writeByte((longValue shr 8).toByte().toInt())
                buffer.writeByte(longValue.toByte().toInt())
                buffer.writeByte((longValue shr 24).toByte().toInt())
                buffer.writeByte((longValue shr 16).toByte().toInt())
            }
            DataOrder.INVERSED_MIDDLE ->
            {
                check(transformation == DataTransformation.NONE) { "Inversed middle endian cannot be transformed." }
                check(type == DataType.INT) { "Inversed middle endian can only be used with an integer." }
                buffer.writeByte((longValue shr 16).toByte().toInt())
                buffer.writeByte((longValue shr 24).toByte().toInt())
                buffer.writeByte(longValue.toByte().toInt())
                buffer.writeByte((longValue shr 8).toByte().toInt())
            }
        }
    }

    /**
     * Puts a standard data type with the specified value and byte order.
     *
     * @param type The data type.
     * @param order The byte order.
     * @param value The value.
     */
    fun put(
            type: DataType,
            order: DataOrder,
            value: Number
    ) = put(type, order, DataTransformation.NONE, value)

    /**
     * Puts a standard data type with the specified value and transformation.
     *
     * @param type The type.
     * @param transformation The transformation.
     * @param value The value.
     */
    fun put(
            type: DataType,
            transformation: DataTransformation,
            value: Number
    ) = put(type, DataOrder.BIG, transformation, value)

    /**
     * Puts a standard data type with the specified value.
     *
     * @param type The data type.
     * @param value The value.
     */
    fun put(
            type: DataType,
            value: Number
    ) = put(type, DataOrder.BIG, DataTransformation.NONE, value)

    /**
     * Puts the specified byte array into the buffer.
     *
     * @param bytes The byte array.
     */
    fun putBytes(bytes: ByteArray) = buffer.writeBytes(bytes)

    /**
     * Puts the specified byte array into the buffer.
     *
     * @param bytes The byte array.
     */
    fun putBytes(
            bytes: ByteArray,
            position: Int,
            length: Int
    )
    {
        for (i in position until position + length)
            buffer.writeByte(bytes[i].toInt())
    }

    /**
     * Puts the specified byte array into the buffer.
     *
     * @param bytes The byte array.
     */
    fun putBytes(
            transformation: DataTransformation,
            bytes: ByteArray,
            position: Int,
            length: Int
    )
    {
        for (i in position until position + length)
            put(DataType.BYTE, transformation, bytes[i].toInt())
    }

    /**
     * Puts the bytes from the specified buffer into this packet's buffer.
     *
     * @param buffer The source [ByteBuf].
     */
    fun putBytes(buffer: ByteBuf)
    {
        val bytes = ByteArray(buffer.readableBytes())
        buffer.markReaderIndex()
        try
        {
            buffer.readBytes(bytes)
        }
        finally
        {
            buffer.resetReaderIndex()
        }
        putBytes(bytes)
    }

    /**
     * Puts the bytes into the buffer with the specified transformation.
     *
     * @param transformation The transformation.
     * @param bytes The byte array.
     */
    fun putBytes(
            transformation: DataTransformation,
            bytes: ByteArray
    )
    {
        if (transformation == DataTransformation.NONE)
            putBytes(bytes)
        else
            for (b in bytes)
                put(DataType.BYTE, transformation, b)
    }

    fun putBytes(
            transformation: DataTransformation,
            buffer: ByteBuf
    )
    {
        val bytes = ByteArray(buffer.readableBytes())
        buffer.markReaderIndex()
        try
        {
            buffer.readBytes(bytes)
        }
        finally
        {
            buffer.resetReaderIndex()
        }
        putBytes(transformation, bytes)
    }

    /**
     * Puts the specified byte array into the buffer in reverse.
     *
     * @param bytes The byte array.
     */
    fun putBytesReverse(bytes: ByteArray)
    {

        for (i in bytes.indices.reversed())
            buffer.writeByte(bytes[i].toInt())

    }

    /**
     * Puts the bytes from the specified buffer into this packet's buffer, in reverse.
     *
     * @param buffer The source [ByteBuf].
     */
    fun putBytesReverse(buffer: ByteBuf)
    {
        val bytes = ByteArray(buffer.readableBytes())
        buffer.markReaderIndex()
        try
        {
            buffer.readBytes(bytes)
        }
        finally
        {
            buffer.resetReaderIndex()
        }
        putBytesReverse(bytes)
    }

    /**
     * Puts the specified byte array into the buffer in reverse with the specified transformation.
     *
     * @param transformation The transformation.
     * @param bytes The byte array.
     */
    fun putBytesReverse(
            transformation: DataTransformation,
            bytes: ByteArray
    )
    {

        if (transformation == DataTransformation.NONE)
            putBytesReverse(bytes)
        else
            for (i in bytes.indices.reversed())
                put(DataType.BYTE, transformation, bytes[i])

    }

    /**
     * Puts a smart into the buffer.
     *
     * @param value The value.
     */
    fun putSmart(value: Int)
    {

        if (value >= 0x80)
            buffer.writeShort(value + 0x8000)
        else
            buffer.writeByte(value)

    }

    /**
     * Puts a string into the buffer.
     *
     * @param str The string.
     */
    fun putString(str: String)
    {

        val chars = str.toCharArray()

        for (c in chars)
            buffer.writeByte(c.toByte().toInt())

        buffer.writeByte(0)

    }

    fun toBufferedReader() : BufferedReader = BufferedReader(byteBuf.array().copyOfRange(0, byteBuf.readableBytes()))

    fun toRawBufferedReader() : BufferedReader = BufferedReader(byteBuf.array())

}