package rs.emulator.definitions.factories

import rs.emulator.Repository
import rs.emulator.definitions.DefinitionFactory
import rs.emulator.definitions.entity.obj.ObjMetaDataDefinition

object ItemMetaDefinitionFactory : DefinitionFactory<ObjMetaDataDefinition>() {
    override fun provide(identifier: Int, block: ObjMetaDataDefinition.() -> Unit): ObjMetaDataDefinition {
        val meta : ObjMetaDataDefinition = Repository.getDefinition(identifier)
        block(meta)
        return meta
    }
}