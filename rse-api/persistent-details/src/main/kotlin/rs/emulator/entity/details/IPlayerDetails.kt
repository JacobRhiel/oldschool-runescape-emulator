package rs.emulator.entity.details

/**
 *
 * @author javatar
 */

interface IPlayerDetails {

    val username: String
    var displayName: String
    var coordinate: Int
    val privilege: IPrivilege
    var inventory: String
    var bank: String
    var equipment: String
    var varbits: String
    var banned: Boolean
    var muted: Boolean
    var attributes: MutableMap<String, String>

}