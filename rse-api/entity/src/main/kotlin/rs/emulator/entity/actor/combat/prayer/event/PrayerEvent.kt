package rs.emulator.entity.actor.combat.prayer.event

import rs.emulator.entity.actor.combat.prayer.IPrayer

/**
 *
 * @author Chk
 */
interface PrayerEvent<out I : IPrayer>
{

    val prayer: I

    var ignored: Boolean

}