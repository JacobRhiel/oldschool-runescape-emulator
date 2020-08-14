package rs.emulator.entity.actor.combat.prayer.event.impl

import rs.emulator.entity.actor.combat.prayer.IPrayer
import rs.emulator.entity.actor.combat.prayer.event.PrayerEvent

/**
 *
 * @author Chk
 */
class TogglePrayerEvent(override val prayer: IPrayer, override var ignored: Boolean = false) : PrayerEvent<IPrayer>
{
}