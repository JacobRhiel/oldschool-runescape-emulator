package rs.emulator.service.login

/**
 *
 * @author Chk
 */
enum class LoginStatus(val opcode: Int)
{

    ACCEPTED(2),

    INVALID_CREDENTIALS(3),

    BANNED(4),

    ALREADY_ONLINE(5),

    WORLD_FULL(7),

    LOGIN_THROTTLE(16),//?

    CLOSED_BETA(19),

    ERROR_LOADING(24),

    AUTHENTICATION_REQUEST(56),

    AUTHENTICATION_MISMATCH(57)

    ;

    companion object
    {

        fun fetchStatusForOpcode(opcode: Int) = values().firstOrNull { it.opcode == opcode }

    }

}