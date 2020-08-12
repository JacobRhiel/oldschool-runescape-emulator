package rs.emulator.plugins.extensions.factories.actions

import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface ReportAbuseAction {

    //TODO - above abuse: Int to enum of abuse types
    fun handleReportedAbuse(player : IPlayer, name : String, abuse : Int, isStaff : Boolean)

}