package rs.emulator.service.login.network.message

import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import rs.emulator.network.message.NetworkMessage
import rs.emulator.service.login.LoginCredentials

/**
 *
 * @author Chk
 */
data class LoginRequestMessage(val ctx: ChannelHandlerContext,
                               val channel: Channel,
                               val credentials: LoginCredentials
) : NetworkMessage