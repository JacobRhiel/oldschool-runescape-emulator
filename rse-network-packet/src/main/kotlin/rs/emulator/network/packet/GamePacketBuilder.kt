package rs.emulator.network.packet

import com.google.common.base.Preconditions
import io.netty.buffer.Unpooled
import rs.emulator.buffer.DataConstants
import rs.emulator.buffer.access.AccessMode
import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.writer.BufferedWriter

/**
 *
 * @author Tom <rspsmods@gmail.com>
 * @author Chk
 */
class GamePacketBuilder : BufferedWriter
{

    /**
     * The current mode.
     */
    private var mode = AccessMode.BYTE_ACCESS

    /**
     * The opcode.
     */
    private val opcode: Int

    /**
     * The [PacketType].
     */
    private val packetType: PacketType

    /**
     * The [ActionType].
     */
    private val actionType: ActionType

    /**
     * Gets the current length of the builder's buffer.
     *
     * @return The length of the buffer.
     */
    override val length: Int
        get()
        {
            checkByteAccess()
            return buffer.writerIndex()
        }

    /**
     * Creates a raw [GamePacketBuilder].
     */
    constructor() : this(-1, PacketType.RAW, ActionType.NONE)

    /**
     * Creates the [GamePacketBuilder] for the specified packet type and opcode.
     *
     * @param opcode The opcode.
     * @param packetType The packet type.
     * @param actionType The action type.
     */
    @JvmOverloads
    constructor(opcode: Int,
                packetType: PacketType = PacketType.FIXED,
                actionType: ActionType = ActionType.NONE
    ) : super(Unpooled.buffer(1))
    {
        this.opcode = opcode
        this.packetType = packetType
        this.actionType = actionType
    }

    /**
     * Checks that this builder is in the bit access mode.
     *
     * @throws IllegalStateException If the builder is not in bit access mode.
     */
    private fun checkBitAccess() = Preconditions.checkState(mode === AccessMode.BIT_ACCESS, "For bit-based calls to work, the mode must be bit access.")

    /**
     * Checks that this builder is in the byte access mode.
     *
     * @throws IllegalStateException If the builder is not in byte access mode.
     */
    private fun checkByteAccess() = Preconditions.checkState(mode === AccessMode.BYTE_ACCESS, "For byte-based calls to work, the mode must be byte access.")

    override fun put(type: DataType, order: DataOrder, transformation: DataTransformation, value: Number)
    {
        checkByteAccess()
        super.put(type, order, transformation, value)
    }

    /**
     * Puts `numBits` into the buffer with the value `value`.
     *
     * @param numBits The number of bits to put into the buffer.
     * @param value The value.
     * @throws IllegalArgumentException If the number of bits is not between 1 and 31 inclusive.
     */
    @Throws(IllegalArgumentException::class)
    fun putBits(numBits: Int, value: Int)
    {

        var numberOfBits = numBits

        Preconditions.checkArgument(numberOfBits in 1..32, "Number of bits must be between 1 and 32 inclusive.")

        checkBitAccess()

        var bytePos = bitIndex shr 3
        var bitOffset = 8 - (bitIndex and 7)
        bitIndex += numberOfBits

        var requiredSpace = bytePos - buffer.writerIndex() + 1
        requiredSpace += (numberOfBits + 7) / 8
        buffer.ensureWritable(requiredSpace)

        while (numberOfBits > bitOffset)
        {

            var tmp = buffer.getByte(bytePos).toInt()
            tmp = tmp and DataConstants.BIT_MASK[bitOffset].inv()
            tmp = tmp or (value shr numberOfBits - bitOffset and DataConstants.BIT_MASK[bitOffset])
            buffer.setByte(bytePos++, tmp)
            numberOfBits -= bitOffset
            bitOffset = 8

        }

        var tmp = buffer.getByte(bytePos).toInt()

        if (numberOfBits == bitOffset)
        {

            tmp = tmp and DataConstants.BIT_MASK[bitOffset].inv()
            tmp = tmp or (value and DataConstants.BIT_MASK[bitOffset])
            buffer.setByte(bytePos, tmp)

        }
        else
        {

            tmp = tmp and (DataConstants.BIT_MASK[numberOfBits] shl bitOffset - numberOfBits).inv()
            tmp = tmp or (value and DataConstants.BIT_MASK[numberOfBits] shl bitOffset - numberOfBits)
            buffer.setByte(bytePos, tmp)

        }
    }

    override fun putBytesReverse(bytes: ByteArray)
    {
        checkByteAccess()
        super.putBytesReverse(bytes)
    }

    override fun putSmart(value: Int)
    {
        checkByteAccess()
        super.putSmart(value)
    }

    override fun putString(str: String)
    {
        checkByteAccess()
        super.putString(str)
    }

    /**
     * Puts a raw builder. Both builders (this and parameter) must be in byte access mode.
     *
     * @param builder The builder.
     * @throws IllegalArgumentException If the builder is not raw.
     */
    fun putRawBuilder(builder: GamePacketBuilder)
    {

        checkByteAccess()

        if (builder.packetType != PacketType.RAW)
            throw IllegalArgumentException("Builder must be raw.")

        builder.checkByteAccess()

        putBytes(builder.buffer)

    }

    /**
     * Puts a raw builder in reverse. Both builders (this and parameter) must be in byte access mode.
     *
     * @param builder The builder.
     * @throws IllegalArgumentException If the builder is not raw.
     */
    fun putRawBuilderReverse(builder: GamePacketBuilder)
    {

        checkByteAccess()

        Preconditions.checkArgument(builder.packetType == PacketType.RAW, "Builder must be raw.")

        builder.checkByteAccess()

        putBytesReverse(builder.buffer)

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

        bitIndex = buffer.writerIndex() * 8

    }

    /**
     * Switches this builder's mode to the byte access mode.
     *
     * @throws IllegalStateException If the builder is already in byte access mode.
     */
    fun switchToByteAccess()
    {

        Preconditions.checkState(mode !== AccessMode.BYTE_ACCESS, "Already in bit access mode.")

        mode = AccessMode.BYTE_ACCESS

        buffer.writerIndex((bitIndex + 7) / 8)

    }

    /**
     * Creates a [GamePacket] based on the current contents of this builder.
     *
     * @return The [GamePacket].
     * @throws IllegalStateException If the builder is not in byte access mode, or if the packet is raw.
     */
    fun toGamePacket(): GamePacket
    {

        Preconditions.checkState(packetType != PacketType.RAW, "Raw packets cannot be converted to a game packet.")

        Preconditions.checkState(mode === AccessMode.BYTE_ACCESS, "Must be in byte access mode to convert to a packet.")

        return GamePacket(opcode, actionType, packetType, buffer)

    }

}