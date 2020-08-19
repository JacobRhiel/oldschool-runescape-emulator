package rs.emulator.entity.actor.combat

import rs.emulator.entity.actor.combat.prayer.PrayerManager
import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author Chk
 */
class CombatFactory(private val player: IPlayer)
{

    val prayerManager: PrayerManager = PrayerManager(player)



}