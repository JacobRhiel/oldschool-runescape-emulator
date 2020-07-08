package rs.emulator.plugin.commands.impl

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default

/**
 *
 * @author javatar
 */

class SpawnItem (parser : ArgParser) {

    val id by parser.positional("The item to spawn") { toInt() }
    val amt by parser.positional("The amount to spawn") { toInt() }.default(1)

}