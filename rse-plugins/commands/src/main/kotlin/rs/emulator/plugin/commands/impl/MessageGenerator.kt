package rs.emulator.plugin.commands.impl

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default

/**
 *
 * @author javatar
 */

class MessageGenerator(parser: ArgParser) {

    val msg by parser.positional("Game Message")
    val type by parser.positional("Message Type") { toInt() }.default(0)

}