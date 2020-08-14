package rs.emulator.plugins.extensions.factories.entity.examine.actions

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.plugins.extensions.factories.entity.examine.ExamineType

/**
 *
 * @author javatar
 */

interface ExamineAction {

    fun examine(
        player: IPlayer,
        entityType: ExamineType
    )

}