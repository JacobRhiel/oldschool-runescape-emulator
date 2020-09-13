package rs.emulator.entity.actor.combat.prayer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import rs.emulator.entity.actor.combat.prayer.event.impl.ActivatePrayerEvent
import rs.emulator.entity.actor.combat.prayer.event.impl.DeActivatePrayerEvent
import rs.emulator.entity.actor.combat.prayer.event.impl.TogglePrayerEvent
import rs.emulator.entity.actor.player.IPlayer
import java.util.*

/**
 *
 * @author Chk
 */
class PrayerManager(private val player: IPlayer)
{

    private var activePrayers = EnumSet.noneOf(Prayers::class.java)

    fun hasRequirements(prayer: Prayers) : Boolean
    {

        return true

    }

    fun togglePrayer(prayer: Prayers) = flow {

        val togglePrayerEvent = TogglePrayerEvent(prayer)

        emit(togglePrayerEvent)

        if(togglePrayerEvent.ignored)
            return@flow

        if(isPrayerActive(prayer))
            deActivatePrayer(prayer)
        else
            activatePrayer(prayer)

    }.launchIn(CoroutineScope(Dispatchers.Unconfined))

    private fun activatePrayer(prayer: Prayers)= flow {

        val activationEvent = ActivatePrayerEvent(prayer)

        emit(activationEvent)

        if (activationEvent.ignored)
            return@flow

        val invalidatedPrayers = prayer.fetchInvalidationPrayers()

        if (invalidatedPrayers.isNotEmpty())
            invalidatedPrayers.filter { isPrayerActive(it) }.forEach { deActivatePrayerNonFlow(it) }

        activePrayers.add(prayer)

        if(prayer.overheadIcon != PrayerIcon.NONE)
        {
            player.prayerIcon = prayer.overheadIcon.id
            //todo: listener for var change?
            player.update()
        }

    }.launchIn(CoroutineScope(Dispatchers.Unconfined))

    private fun deActivatePrayer(prayer: Prayers) = flow {

        val deActivateEvent = DeActivatePrayerEvent(prayer)

        emit(deActivateEvent)

        if(deActivateEvent.ignored)
            return@flow

        activePrayers.remove(prayer)

    }.launchIn(CoroutineScope(Dispatchers.Unconfined))

    private fun deActivatePrayerNonFlow(prayer: Prayers) = activePrayers.remove(prayer)

    fun isPrayerActive(prayer: Prayers)= activePrayers.contains(prayer)

}