package rs.emulator.cache.store.access

/**
 *
 * @author Chk
 */
enum class AccessType(val rafAccess: String)
{

    READ("r"),

    WRITE("w"),

    READ_AND_WRITE("rw")

}