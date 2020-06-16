package rs.emulator

/**
 *
 * @author javatar
 */

interface Repository {

    fun <T : Definition> findDefinition(identifier: Int, child: Int = -1): T

}