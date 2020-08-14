package rs.emulator.plugins.extensions.factories.entity.examine

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.entity.examine.actions.ExamineAction

/**
 *
 * @author javatar
 */

interface ExamineActionFactory : ExtensionPoint {

    fun registerExamineAction(
        entityType: Int,
        id: Int
    ) : ExamineAction

}