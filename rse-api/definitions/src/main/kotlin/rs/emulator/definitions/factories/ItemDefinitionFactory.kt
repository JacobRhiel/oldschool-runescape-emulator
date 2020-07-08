package rs.emulator.definitions.factories

import rs.emulator.Repository
import rs.emulator.definitions.DefinitionFactory
import rs.emulator.definitions.entity.loc.LocDefinition
import rs.emulator.definitions.entity.obj.ObjDefinition

/**
 *
 * @author javatar
 */

object ItemDefinitionFactory : DefinitionFactory<ObjDefinition>() {

    override fun provide(identifier: Int, block : ObjDefinition.() -> Unit): ObjDefinition {
        val def : ObjDefinition = Repository.getDefinition(identifier)
        block(def)
        return def
    }

}