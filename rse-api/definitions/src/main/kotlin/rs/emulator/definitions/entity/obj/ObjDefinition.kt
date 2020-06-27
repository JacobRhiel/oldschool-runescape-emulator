package rs.emulator.definitions.entity.obj

import rs.emulator.definitions.Definition

/**
 *
 * @author Chk
 */
data class ObjDefinition(
    val identifier: Int,
    var name: String = "",
    var stacks: Boolean = false,
    var cost: Int = 0,
    var members: Boolean = false,
    val groundMenu: Array<String?> = Array(5) { null },
    val inventoryMenu: Array<String?> = Array(5) { null },
    val equipmentMenu: Array<String?> = Array(8) { null },
    var grandExchange: Boolean = false,
    var teamCape: Int = 0,
    var noteLinkId: Int = 0,
    var noteTemplateId: Int = 0,
    var placeholderLink: Int = 0,
    var placeholderTemplate: Int = 0,
    val params: HashMap<Int, Any> = hashMapOf()
) : Definition(identifier)
{


    /**
     * Custom metadata.
     */
    var examine: String? = null
    var tradeable = false
    var weight = 0.0
    var attackSpeed = -1
    var equipSlot = -1
    var equipType = 0
    var weaponType = -1
    var renderAnimations: IntArray? = null
    //var skillReqs: Byte2ByteOpenHashMap? = null
    lateinit var bonuses: IntArray

    val stackable: Boolean
        get() = stacks || noteTemplateId > 0

    val noted: Boolean
        get() = noteTemplateId > 0

    val isPlaceholder
        get() = placeholderTemplate > 0 && placeholderLink > 0

}