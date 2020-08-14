package rs.emulator.entity.actor.combat.prayer

/**
 *
 * @author Chk
 */
interface IPrayer
{

    val ordinal: Int

    val child: Int

    val type: PrayerType

    val drainPerMinute: Double

    val invalidationPrayers: Array<out PrayerType>

    fun fetchInvalidationPrayers() : List<IPrayer>

}