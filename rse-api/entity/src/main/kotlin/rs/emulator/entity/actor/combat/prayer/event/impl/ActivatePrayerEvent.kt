package rs.emulator.entity.actor.combat.prayer.event.impl

import rs.emulator.entity.actor.combat.prayer.IPrayer
import rs.emulator.entity.actor.combat.prayer.Prayers
import rs.emulator.entity.actor.combat.prayer.event.PrayerEvent

/**
 *
 * @author Chk
 */
class ActivatePrayerEvent(override val prayer: Prayers, override var ignored: Boolean = false) : PrayerEvent<Prayers>
{
}