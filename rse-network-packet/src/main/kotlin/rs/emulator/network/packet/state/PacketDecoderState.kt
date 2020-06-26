package rs.emulator.network.packet.state

/**
 *
 * @author Chk
 */
enum class PacketDecoderState
{

    OPCODE,

    LENGTH,

    PAYLOAD

}