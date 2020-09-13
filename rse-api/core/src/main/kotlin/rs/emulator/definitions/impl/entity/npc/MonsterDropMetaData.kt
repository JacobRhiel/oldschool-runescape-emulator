package rs.emulator.definitions.impl.entity.npc

/**
 *
 * @author Chk
 */
data class MonsterDropMetaData(
    val id: Int,
    val name: String,
    val members: Boolean,
    val quantity: String,
    val noted: Boolean,
    val rarity: Float,
    val drop_requirements: String
)