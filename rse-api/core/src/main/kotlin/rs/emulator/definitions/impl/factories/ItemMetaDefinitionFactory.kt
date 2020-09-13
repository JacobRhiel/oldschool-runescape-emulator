package rs.emulator.definitions.impl.factories

import rs.emulator.definitions.DefinitionFactory
import rs.emulator.definitions.Repository
import rs.emulator.definitions.impl.entity.obj.ObjMetaDataDefinition

object ItemMetaDefinitionFactory : DefinitionFactory<ObjMetaDataDefinition>() {
    override fun provide(identifier: Int, block: ObjMetaDataDefinition.() -> Unit): ObjMetaDataDefinition {
        val meta : ObjMetaDataDefinition = Repository.getDefinition(identifier)
        block(meta)
        return meta
    }
}