package rs.emulator.plugin

import org.pf4j.Extension
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.messages.chat.ChatMessageType
import rs.emulator.plugins.extensions.factories.LoginActionFactory
import rs.emulator.plugins.extensions.factories.ReportAbuseFactory
import rs.emulator.plugins.extensions.factories.actions.LoginAction
import rs.emulator.plugins.extensions.factories.actions.ReportAbuseAction

/**
 *
 * @author javatar
 */
@Extension(plugins = ["REPORT-ABUSE"])
class ReportAbuseExtension : ReportAbuseFactory, ReportAbuseAction {
    override fun registerReportAbuseAction(name: String, abuse: Int, isStaff: Boolean): ReportAbuseAction {
        return this
    }

    override fun handleReportedAbuse(player : IPlayer, name: String, abuse: Int, isStaff: Boolean) {
        player.messages().sendChatMessage("You reported $name.")
    }


}