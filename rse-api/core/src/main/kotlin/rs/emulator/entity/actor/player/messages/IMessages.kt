package rs.emulator.entity.actor.player.messages

/**
 *
 * @author javatar
 */

interface IMessages {

    fun sendSkillUpdate(
        id : Int,
        level : Int,
        experience : Int
    )

    fun sendRebuildRegion(
        login: Boolean,
        index: Int,
        x: Int,
        z: Int,
        height: Int = 0,
        tileHash: Int
    )

}