package rs.emulator.skills

/**
 *
 * @author javatar
 */

fun Skills.wieldRequirementMessage(requirement: Int, name: String): String {
    return "You need an ${this.name.toLowerCase().capitalize()} level of $requirement to wield $name."
}