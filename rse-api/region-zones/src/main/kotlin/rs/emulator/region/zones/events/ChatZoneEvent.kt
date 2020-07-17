package rs.emulator.region.zones.events

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.region.zones.ZoneEvent

/**
 *
 * @author javatar
 */

data class ChatZoneEvent(override val entity: IPlayer, val allowed: Boolean) :
    ZoneEvent<IPlayer>