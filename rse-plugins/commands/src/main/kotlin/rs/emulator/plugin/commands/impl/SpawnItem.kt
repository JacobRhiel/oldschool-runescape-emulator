package rs.emulator.plugin.commands.impl

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default

/**
 *
 * @author javatar
 */

class SpawnItem(parser : ArgParser) {

    val itemId by parser.storing(
        "-id",
        help = "the item id"
    ) { toInt() }

    val amount by parser.storing(
        "-amt",
        help = "the amount of the item"
    ) { toInt() }.default(1)

}