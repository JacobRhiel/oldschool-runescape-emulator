package rs.emulator.cache.definition.entity.obj.meta

import rs.emulator.cache.definition.MetaDataDefinition

/**
 *
 * @author Chk
 */
data class ObjMetaDataDefinition(val identifier: Int,
                                 var examine: String,
                                 var tradeable: Boolean = false,
                                 var equipable_by_player: Boolean = false,
                                 var equipable_weapon: Boolean = false,
                                 var weight: Double = 0.0,
                                 var buy_limit: Int = 0,
                                 var quest_item: Boolean = false,
                                 var weapon: Weapon? = null,
                                 var equipment: Equipment? = null
) : MetaDataDefinition(identifier)
