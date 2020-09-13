package rs.emulator.definitions.impl.factories

import rs.emulator.definitions.DefinitionFactory
import rs.emulator.definitions.Repository
import rs.emulator.definitions.impl.widget.WidgetDefinition

object WidgetDefinitionFactory : DefinitionFactory<WidgetDefinition>() {
    override fun provide(identifier: Int, block: WidgetDefinition.() -> Unit): WidgetDefinition {
        val def = Repository.getDefinition<WidgetDefinition>(identifier)
        block(def)
        return def
    }
}