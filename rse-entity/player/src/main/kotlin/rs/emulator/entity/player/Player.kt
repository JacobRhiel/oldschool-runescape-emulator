package rs.emulator.entity.player

import io.netty.channel.Channel
import rs.emulator.entity.actor.Actor
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.entity.player.update.sync.SyncInformation
import rs.emulator.entity.player.viewport.Viewport

class Player(val channel: Channel) : Actor(), IPlayer
{

    val viewport = Viewport(this)

    val syncInfo = SyncInformation().apply { this.addFlag(PlayerUpdateFlag.APPEARANCE) }

}