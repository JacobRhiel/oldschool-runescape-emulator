package rs.emulator.fileserver.network.protocol

/**
 *
 * @author Chk
 */
enum class JS5ResponseProtocol(
    val opcode: Int
)
{

    OK(0),

    OUT_OF_DATE(6)

    ;

    companion object
    {

        fun fetchProtocol(opcode: Int) = values().firstOrNull { it.opcode == opcode }

    }

}