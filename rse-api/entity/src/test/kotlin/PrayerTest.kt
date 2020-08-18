import org.junit.jupiter.api.Test
import rs.emulator.entity.actor.combat.prayer.Prayers
import rs.emulator.entity.actor.combat.prayer.PrayerManager

/**
 *
 * @author Chk
 */
class PrayerTest
{

    @Test
    fun isActive()
    {

        /*val manager = PrayerManager()

        manager.togglePrayer(Prayers.STEEL_SKIN)

        assert(manager.isPrayerActive(Prayers.STEEL_SKIN))*/

    }

    @Test
    fun isInactive()
    {

       /* val manager = PrayerManager()

        manager.togglePrayer(Prayers.STEEL_SKIN)

        assert(manager.isPrayerActive(Prayers.STEEL_SKIN))

        manager.togglePrayer(Prayers.STEEL_SKIN)

        assert(!manager.isPrayerActive(Prayers.STEEL_SKIN))*/

    }

    @Test
    fun doesDisableSameType()
    {

        /*val manager = PrayerManager()

        manager.togglePrayer(Prayers.STEEL_SKIN)

        assert(manager.isPrayerActive(Prayers.STEEL_SKIN))

        manager.togglePrayer(Prayers.ROCK_SKIN)

        assert(manager.isPrayerActive(Prayers.ROCK_SKIN) && !manager.isPrayerActive(Prayers.STEEL_SKIN))*/

    }

    @Test
    fun containsMultiplePrayers()
    {

        /*val manager = PrayerManager()

        manager.togglePrayer(Prayers.STEEL_SKIN)

        manager.togglePrayer(Prayers.BURST_OF_STRENGTH)

        assert(manager.isPrayerActive(Prayers.STEEL_SKIN))

        assert(manager.isPrayerActive(Prayers.BURST_OF_STRENGTH))

        manager.togglePrayer(Prayers.CHIVALRY)

        assert(!manager.isPrayerActive(Prayers.BURST_OF_STRENGTH))
        assert(!manager.isPrayerActive(Prayers.STEEL_SKIN))
        assert(manager.isPrayerActive(Prayers.CHIVALRY))

        manager.togglePrayer(Prayers.RIGOUR)
        assert(!manager.isPrayerActive(Prayers.CHIVALRY))
        assert(manager.isPrayerActive(Prayers.RIGOUR))

        manager.togglePrayer(Prayers.PROTECT_FROM_MELEE)
        assert(manager.isPrayerActive(Prayers.PROTECT_FROM_MELEE))
        manager.togglePrayer(Prayers.PROTECT_FROM_MAGIC)
        assert(!manager.isPrayerActive(Prayers.PROTECT_FROM_MELEE))
        assert(manager.isPrayerActive(Prayers.PROTECT_FROM_MAGIC))
        assert(manager.isPrayerActive(Prayers.RIGOUR))*/

    }

}