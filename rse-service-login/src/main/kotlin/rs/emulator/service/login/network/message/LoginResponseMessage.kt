package rs.emulator.service.login.network.message

import rs.emulator.network.message.NetworkMessage
import rs.emulator.service.login.LoginResult

/**
 *
 * @author Chk
 */
class LoginResponseMessage(val isaac: IntArray, val response: LoginResult) : NetworkMessage