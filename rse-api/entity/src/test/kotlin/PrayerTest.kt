import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.junit.jupiter.api.Test
import rs.emulator.entity.actor.combat.prayer.PlayerPrayer
import rs.emulator.entity.actor.combat.prayer.PrayerManager
import rs.emulator.entity.actor.combat.prayer.event.impl.ActivatePrayerEvent
import rs.emulator.entity.actor.combat.prayer.event.impl.DeActivatePrayerEvent
import rs.emulator.reactive.onEachInstance

/**
 *
 * @author Chk
 */
class PrayerTest
{

    @Test
    fun isActive()
    {

        val manager = PrayerManager()

        manager.activatePrayer(PlayerPrayer.STEEL_SKIN)

        assert(manager.isPrayerActive(PlayerPrayer.STEEL_SKIN))

    }

    @Test
    fun isInactive()
    {

        val manager = PrayerManager()

        manager.activatePrayer(PlayerPrayer.STEEL_SKIN)

        assert(manager.isPrayerActive(PlayerPrayer.STEEL_SKIN))

        manager.deActivatePrayer(PlayerPrayer.STEEL_SKIN)

        assert(!manager.isPrayerActive(PlayerPrayer.STEEL_SKIN))

    }

    @Test
    fun doesDisableSameType()
    {

        val manager = PrayerManager()

        manager.togglePrayer(PlayerPrayer.STEEL_SKIN)

        assert(manager.isPrayerActive(PlayerPrayer.STEEL_SKIN))

        manager.togglePrayer(PlayerPrayer.ROCK_SKIN)

        assert(manager.isPrayerActive(PlayerPrayer.ROCK_SKIN) && !manager.isPrayerActive(PlayerPrayer.STEEL_SKIN))

    }

    @Test
    fun containsMultiplePrayers()
    {

        val manager = PrayerManager()

        manager.togglePrayer(PlayerPrayer.STEEL_SKIN)

        manager.togglePrayer(PlayerPrayer.BURST_OF_STRENGTH)

        assert(manager.isPrayerActive(PlayerPrayer.STEEL_SKIN))

        assert(manager.isPrayerActive(PlayerPrayer.BURST_OF_STRENGTH))

        manager.togglePrayer(PlayerPrayer.SUPERHUMAN_STRENGTH)

    }

}