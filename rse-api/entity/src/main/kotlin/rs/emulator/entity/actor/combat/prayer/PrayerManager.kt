package rs.emulator.entity.actor.combat.prayer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import rs.emulator.entity.actor.combat.prayer.event.PrayerEvent
import rs.emulator.entity.actor.combat.prayer.event.impl.ActivatePrayerEvent
import rs.emulator.entity.actor.combat.prayer.event.impl.DeActivatePrayerEvent
import rs.emulator.entity.actor.combat.prayer.event.impl.TogglePrayerEvent
import java.util.*

/**
 *
 * @author Chk
 */
class PrayerManager
{

    private var activePrayers = EnumSet.noneOf(PlayerPrayer::class.java)

    fun hasRequirements(prayer: IPrayer) : Boolean
    {

        return true

    }

    fun togglePrayer(prayer: PlayerPrayer) = flow {

        val togglePrayerEvent = TogglePrayerEvent(prayer)

        emit(togglePrayerEvent)

        if(togglePrayerEvent.ignored)
            return@flow

        if(isPrayerActive(prayer))
        {
            println("is active")
            deActivatePrayer(prayer)
        }
        else
            activatePrayer(prayer)

    }.launchIn(CoroutineScope(Dispatchers.Unconfined))

    private fun activatePrayer(prayer: PlayerPrayer)= flow {

        val activationEvent = ActivatePrayerEvent(prayer)

        emit(activationEvent)

        if (activationEvent.ignored)
            return@flow

        val invalidatedPrayers = prayer.fetchInvalidationPrayers()

        if (invalidatedPrayers.isNotEmpty())
        {

            println("invalidation prayers: " + invalidatedPrayers.toTypedArray().contentDeepToString())

            invalidatedPrayers.filter { isPrayerActive(it) }.forEach { deActivatePrayerNonFlow(it) }

        }

        activePrayers.add(prayer)

        println("enabling prayer: $prayer")

    }.launchIn(CoroutineScope(Dispatchers.Unconfined))

    private fun deActivatePrayer(prayer: PlayerPrayer) = flow {

        val deActivateEvent = DeActivatePrayerEvent(prayer)

        emit(deActivateEvent)

        if(deActivateEvent.ignored)
            return@flow

        activePrayers.remove(prayer)

        println("deactivating: $prayer")

    }.launchIn(CoroutineScope(Dispatchers.Unconfined))

    private fun deActivatePrayerNonFlow(prayer: PlayerPrayer)
    {
        activePrayers.remove(prayer)
        println("deactivating: $prayer")
    }

    fun isPrayerActive(prayer: IPrayer)= activePrayers.contains(prayer)

}