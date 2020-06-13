package rs.emulator.cache.definition.entity.obj.meta

data class Equipment(
    val attack_crush: Int,
    val attack_magic: Int,
    val attack_ranged: Int,
    val attack_slash: Int,
    val attack_stab: Int,
    val defence_crush: Int,
    val defence_magic: Int,
    val defence_ranged: Int,
    val defence_slash: Int,
    val defence_stab: Int,
    val magic_damage: Int,
    val melee_strength: Int,
    val prayer: Int,
    val ranged_strength: Int,
    val requirements: Requirements,
    val slot: String
)