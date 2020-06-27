package rs.emulator.definitions.factories

import rs.emulator.Repository
import rs.emulator.definitions.DefinitionFactory
import rs.emulator.definitions.entity.npc.NpcDefinition

object NpcDefinitionFactory : DefinitionFactory<NpcDefinition>() {
    override fun provide(identifier: Int, block: NpcDefinition.() -> Unit): NpcDefinition {
        val def : NpcDefinition = Repository.getDefinition(identifier)
        block(def)
        return def
    }
}