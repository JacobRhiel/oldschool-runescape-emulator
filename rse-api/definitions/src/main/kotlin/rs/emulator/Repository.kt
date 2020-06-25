package rs.emulator

import rs.emulator.definitions.Definition

/**
 *
 * @author javatar
 */

object Repository {

    lateinit var repository : IRepository

    inline fun <reified T : Definition> getDefinition(id : Int, secondId : Int = -1) : T {
        return repository.getDefinition(id, secondId)
    }

}