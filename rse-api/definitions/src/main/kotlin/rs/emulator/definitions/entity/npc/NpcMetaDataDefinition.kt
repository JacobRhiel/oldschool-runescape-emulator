package rs.emulator.definitions.entity.npc

import rs.emulator.definitions.MetaDataDefinition

/**
 *
 * @author Chk
 */
data class NpcMetaDataDefinition(
    override val id: Int,
    val name: String,
    val incomplete: Boolean,
    val members: Boolean,
    val combat_level: Int,
    val size: Int,
    val hitpoints: Int,
    val max_hit: Int,
    val attack_type: List<String>,
    val attack_speed: Int,
    val aggressive: Boolean,
    val poisonous: Boolean,
    val immune_poison: Boolean,
    val immune_venom: Boolean,
    val attributes: List<String>,
    val category: List<String>,
    val slayer_monster: Boolean,
    val slayer_level: Int,
    val slayer_xp: Float,
    val slayer_masters: List<String>,
    val duplicate: Boolean,
    val examine: String,
    val attack_level: Int,
    val strength_level: Int,
    val defence_level: Int,
    val magic_level: Int,
    val ranged_level: Int,
    val attack_stab: Int,
    val attack_slash: Int,
    val attack_crush: Int,
    val attack_magic: Int,
    val attack_ranged: Int,
    val defence_stab: Int,
    val defence_slash: Int,
    val defence_crush: Int,
    val defence_magic: Int,
    val defence_ranged: Int,
    val attack_accuracy: Int,
    val melee_strength: Int,
    val ranged_strength: Int,
    val magic_damage: Int,
    val drops: List<MonsterDropMetaData>
) : MetaDataDefinition(id)
{
}