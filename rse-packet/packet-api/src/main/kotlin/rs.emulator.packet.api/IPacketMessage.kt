package rs.emulator.packet.api

import io.netty.buffer.ByteBuf

/**
 *
 * @author Chk
 */
interface IPacketMessage
{

    val opcode : Int
    val action : ActionType
    val type : PacketType
    val payload : ByteBuf

}