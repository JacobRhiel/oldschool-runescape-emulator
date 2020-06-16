package rs.emulator.fileserver.network.protocol

/**
 *
 * @author Chk
 */
enum class JS5RequestProtocol(
    private val opcode: Int
)
{

    REQUEST_PREFETCH(0),

    REQUEST_URGENT(1),

    LOGOUT_NOTIFY(2),

    LOGIN_NOTIFY(3),

    SETUP_XOR(4)

    ;

    companion object
    {

        fun fetchProtocol(opcode: Int) = values().firstOrNull { it.opcode == opcode }

    }

}