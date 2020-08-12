package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.ReportAbuseAction

/**
 *
 * @author javatar
 */

interface ReportAbuseFactory : ExtensionPoint {

    fun registerReportAbuseAction(
        name : String,
        abuse : Int,
        isStaff : Boolean
    ) : ReportAbuseAction

}