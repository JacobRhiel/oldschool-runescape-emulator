package rs.emulator.definitions.entity.obj

data class Weapon(
    val attack_speed: Int,
    val stances: List<Stance>,
    val weapon_type: String
)