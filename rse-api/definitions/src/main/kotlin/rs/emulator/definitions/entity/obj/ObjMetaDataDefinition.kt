package rs.emulator.definitions.entity.obj

import rs.emulator.definitions.MetaDataDefinition

/**
 *
 * @author Chk
 */
data class ObjMetaDataDefinition(
    override val id: Int,
    var examine: String,
    var tradeable: Boolean,
    var equipable_by_player: Boolean,
    var equipable_weapon: Boolean,
    val linked_id_item: Int,
    val linked_id_noted: Int,
    val linked_id_placeholder: Int,
    val placeholder: Boolean,
    val equipable: Boolean,
    var weight: Double,
    var buy_limit: Int,
    var quest_item: Boolean,
    var weapon: Weapon,
    var equipment: Equipment,
    val wiki_name: String,
    val wiki_url: String,
    val wiki_exchange: String
) : MetaDataDefinition(id)
