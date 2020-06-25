package rs.emulator.definitions.factories

import rs.emulator.Repository
import rs.emulator.definitions.DefinitionFactory
import rs.emulator.definitions.entity.loc.LocDefinition

/**
 *
 * @author javatar
 */

class ItemDefinitionFactory : DefinitionFactory<LocDefinition>() {

    override fun provide(identifier: Int, block : LocDefinition.() -> Unit): LocDefinition {
        val def : LocDefinition = Repository.getDefinition(identifier)
        block(def)
        return def
    }



}