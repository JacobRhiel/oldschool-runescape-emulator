package rs.emulator.cache.definition.entity.obj.meta

data class Weapon(
    val attack_speed: Int,
    val stances: List<Stance>,
    val weapon_type: String
)