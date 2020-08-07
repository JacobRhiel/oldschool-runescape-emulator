package rs.emulator.skills

/**
 *
 * @author javatar
 */

fun Skills.wieldRequirementMessage(requirement: Int, name: String): String {
    return "You need an ${this.name.toLowerCase()} level of $requirement to wield $name."
}