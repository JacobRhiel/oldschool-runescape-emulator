package rs.emulator.definitions.factories

import rs.emulator.Repository
import rs.emulator.definitions.DefinitionFactory
import rs.emulator.definitions.widget.WidgetDefinition

object WidgetDefinitionFactory : DefinitionFactory<WidgetDefinition>() {
    override fun provide(identifier: Int, block: WidgetDefinition.() -> Unit): WidgetDefinition {
        val def = Repository.getDefinition<WidgetDefinition>(identifier)
        block(def)
        return def
    }
}