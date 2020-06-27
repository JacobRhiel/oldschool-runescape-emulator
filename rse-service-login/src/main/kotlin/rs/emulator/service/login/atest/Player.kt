package rs.emulator.service.login.atest

import rs.emulator.database.annotations.NoArg
import rs.emulator.entity.actor.player.IPlayer
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 *
 * @author Chk
 */
@NoArg
@Entity
@Table(name = "players")
open class Player(
    @Column(name = "uid") open val uid: Int = -1,
    @Id @Column(name = "username", unique = true, nullable = false) val username: String,
    @Column(name = "displayName") open val displayName: String = username,
    @Column(name = "password") open val password: String,
    @Column(name = "status") open val status: String = "Banned"
) : IPlayer, Entry
{

    fun login(channel: Channel)
    {
        return this
    }

    override fun containerManager(): IItemContainerManager<ItemContainer<Item>> {
        TODO("Not yet implemented")

        channel.writeAndFlush(RebuildRegionMessage(true, 1, 3087, 3497, tileHash = -1))

    }

    fun get(): Player = this.get(this, username) as Player

}