package rs.emulator.definitions.impl.factories

import rs.emulator.definitions.DefinitionFactory
import rs.emulator.definitions.Repository
import rs.emulator.definitions.impl.entity.npc.NpcDefinition

object NpcDefinitionFactory : DefinitionFactory<NpcDefinition>() {
    override fun provide(identifier: Int, block: NpcDefinition.() -> Unit): NpcDefinition {
        val def : NpcDefinition = Repository.getDefinition(identifier)
        block(def)
        return def
    }
}