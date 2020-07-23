package rs.emulator

import rs.emulator.definitions.Definition
import rs.emulator.definitions.widget.WidgetDefinition

/**
 *
 * @author javatar
 */

object Repository {

    lateinit var definitionRepository: AbstractDefinitionRepository

    inline fun <reified T : Definition> getDefinition(id: Int, secondId: Int = -1): T {
        return definitionRepository.find(id, secondId)
    }

    fun getWidgetDefinition(widgetId: Int): Array<WidgetDefinition> {
        return definitionRepository.findWidget(widgetId)
    }

}