package rs.emulator.entity.actor.combat.prayer.event
import rs.emulator.entity.actor.combat.prayer.Prayers

/**
 *
 * @author Chk
 */
interface PrayerEvent<out I : Prayers>
{

    val prayer: I

    var ignored: Boolean

}