package rs.emulator

import rs.emulator.definitions.Definition

/**
 *
 * @author javatar
 */

object Repository {

    lateinit var definitionRepository : AbstractDefinitionRepository

    inline fun <reified T : Definition> getDefinition(id : Int, secondId : Int = -1) : T {
        return definitionRepository.find(id, secondId)
    }

}