package rs.emulator

import rs.emulator.definitions.Definition

/**
 *
 * @author javatar
 */

interface IRepository {

    fun <T : Definition> getDefinition(identifier : Int, child : Int = -1) : T

}