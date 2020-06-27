package rs.emulator.definitions.factories

import rs.emulator.Repository
import rs.emulator.definitions.DefinitionFactory
import rs.emulator.definitions.entity.obj.ObjDefinition

object ObjectDefinitionFactory : DefinitionFactory<ObjDefinition>() {


    override fun provide(identifier: Int, block: ObjDefinition.() -> Unit): ObjDefinition {
        val def : ObjDefinition = Repository.getDefinition(identifier)
        block(def)
        return def
    }


}