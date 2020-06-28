package rs.emulator.network

import io.netty.util.AttributeKey
import rs.emulator.network.session.NetworkSession

/**
 *
 * @author Chk
 */
val SESSION_KEY: AttributeKey<NetworkSession> = AttributeKey.valueOf("network_session")