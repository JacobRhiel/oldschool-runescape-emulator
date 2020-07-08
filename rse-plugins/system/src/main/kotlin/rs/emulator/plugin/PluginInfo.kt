package rs.emulator.plugin

import com.xenomachina.argparser.ArgParser

/**
 *
 * @author javatar
 */

class PluginInfo(parser : ArgParser) {

    val pluginId : String by parser.positional("") { toUpperCase() }

}