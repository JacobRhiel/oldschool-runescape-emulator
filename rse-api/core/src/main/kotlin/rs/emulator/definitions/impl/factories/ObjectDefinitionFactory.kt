package rs.emulator.definitions.impl.factories

import rs.emulator.definitions.DefinitionFactory
import rs.emulator.definitions.Repository
import rs.emulator.definitions.impl.entity.obj.ObjDefinition

object ObjectDefinitionFactory : DefinitionFactory<ObjDefinition>() {


    override fun provide(identifier: Int, block: ObjDefinition.() -> Unit): ObjDefinition {
        val def : ObjDefinition = Repository.getDefinition(identifier)
        block(def)
        return def
    }


}