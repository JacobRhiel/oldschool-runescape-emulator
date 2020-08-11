package rs.emulator.plugin.commands.impl

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default

/**
 *
 * @author javatar
 */

class SetSkillAttribute(parser: ArgParser) {

    val id by parser.positional("The Skill ID") { toInt() }
    val lvl by parser.positional("The Skill Level") { toInt() }.default(0)

}