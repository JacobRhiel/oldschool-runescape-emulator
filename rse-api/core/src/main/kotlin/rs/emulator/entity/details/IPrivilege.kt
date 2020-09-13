package rs.emulator.entity.details

/**
 *
 * @author javatar
 */

interface IPrivilege {

    val name: String
    val canTalk: Boolean
    val canDrop: Boolean
    val canTrade: Boolean
    val canTeleport: Boolean
    val isSystemAdmin: Boolean

}