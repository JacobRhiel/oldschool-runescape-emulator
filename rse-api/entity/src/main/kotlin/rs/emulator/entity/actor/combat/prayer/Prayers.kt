package rs.emulator.entity.actor.combat.prayer

import rs.emulator.entity.actor.combat.prayer.PrayerType.*

/**
 *
 * @author Chk
 */
enum class Prayers(
    val type: PrayerType,
    val child: Int,
    val drainPerMinute: Double,
    val overheadIcon: PrayerIcon = PrayerIcon.NONE,
    vararg val invalidPrayerTypes: PrayerType = arrayOf()
)
{

    THICK_SKIN(DEFENCE, 5, 5.0, invalidPrayerTypes = *arrayOf(MELEE_COMBAT, NON_MELEE_COMBAT)),

    BURST_OF_STRENGTH(STRENGTH, 6, 5.0, invalidPrayerTypes = *arrayOf(MELEE_COMBAT, NON_MELEE_COMBAT, RANGED, MAGIC)),

    CLARITY_OF_THOUGHT(ATTACK,7, 5.0, invalidPrayerTypes = *arrayOf(MELEE_COMBAT, RANGED, MAGIC)),

    SHARP_EYE(RANGED, 23, 5.0, invalidPrayerTypes = *arrayOf(ATTACK, STRENGTH, MAGIC, MELEE_COMBAT, NON_MELEE_COMBAT)),

    MYSTIC_WILL(MAGIC,24, 5.0, invalidPrayerTypes = *arrayOf(ATTACK, STRENGTH, RANGED, MELEE_COMBAT, NON_MELEE_COMBAT)),

    ROCK_SKIN(DEFENCE, 8, 10.0, invalidPrayerTypes = *arrayOf(MELEE_COMBAT, NON_MELEE_COMBAT)),

    SUPERHUMAN_STRENGTH(STRENGTH, 9, 10.0, invalidPrayerTypes = *arrayOf(MELEE_COMBAT, NON_MELEE_COMBAT, RANGED, MAGIC)),

    IMPROVED_REFLEXES(ATTACK,10, 10.0, invalidPrayerTypes = *arrayOf(MELEE_COMBAT, NON_MELEE_COMBAT, RANGED, MAGIC)),

    RAPID_RESTORE(RESTORATION, 11, 2.3),

    RAPID_HEAL(RESTORATION, 12, 3.3),

    PROTECT_ITEM(EFFECT,13, 3.0),

    HAWK_EYE(RANGED, 25, 10.0, invalidPrayerTypes = *arrayOf(NON_MELEE_COMBAT, MELEE_COMBAT, ATTACK, STRENGTH, MAGIC)),

    MYSTIC_LORE(MAGIC, 26, 10.0, invalidPrayerTypes = *arrayOf(NON_MELEE_COMBAT, MELEE_COMBAT, ATTACK, STRENGTH, RANGED)),

    STEEL_SKIN(DEFENCE, 14, 20.0, invalidPrayerTypes = *arrayOf(MELEE_COMBAT, NON_MELEE_COMBAT)),

    ULTIMATE_STRENGTH(STRENGTH, 15, 20.0, invalidPrayerTypes = *arrayOf(NON_MELEE_COMBAT, MELEE_COMBAT, RANGED, MAGIC)),

    INCREDIBLE_REFLEXES(ATTACK, 16, 20.0, invalidPrayerTypes = *arrayOf(NON_MELEE_COMBAT, MELEE_COMBAT, RANGED, MAGIC)),

    PROTECT_FROM_MAGIC(PROTECTION, 17, 20.0, PrayerIcon.PROTECT_FROM_MAGIC),

    PROTECT_FROM_MISSILES(PROTECTION, 18, 20.0, PrayerIcon.PROTECT_FROM_MISSILES),

    PROTECT_FROM_MELEE(PROTECTION, 19, 20.0, PrayerIcon.PROTECT_FROM_MELEE),

    EAGLE_EYE(RANGED,27, 20.0, invalidPrayerTypes = *arrayOf(MELEE_COMBAT, NON_MELEE_COMBAT)),

    MYSTIC_MIGHT(MAGIC,28, 20.0, invalidPrayerTypes = *arrayOf(MELEE_COMBAT, NON_MELEE_COMBAT)),

    RETRIBUTION(EFFECT, 20, 5.0, PrayerIcon.RETRIBUTION),

    REDEMPTION(RESTORATION,21, 10.0, PrayerIcon.REDEMPTION),

    SMITE(EFFECT, 22, 40.0, PrayerIcon.SMITE),

    PRESERVE(PRESERVATION, 33, 3.0),

    CHIVALRY(MELEE_COMBAT, 29, 40.0, invalidPrayerTypes = *arrayOf(ATTACK, STRENGTH, DEFENCE, RANGED, MAGIC, NON_MELEE_COMBAT)),

    PIETY(MELEE_COMBAT, 30, 40.0, invalidPrayerTypes = *arrayOf(ATTACK, STRENGTH, DEFENCE, RANGED, MAGIC, NON_MELEE_COMBAT)),

    RIGOUR(NON_MELEE_COMBAT, 31, 40.0, invalidPrayerTypes = *arrayOf(ATTACK, STRENGTH, DEFENCE, RANGED, MAGIC, MELEE_COMBAT)),

    AUGURY(NON_MELEE_COMBAT, 32, 40.0, invalidPrayerTypes = *arrayOf(ATTACK, STRENGTH, DEFENCE, RANGED, MAGIC, MELEE_COMBAT))

    ;

    fun fetchInvalidationPrayers() : List<Prayers>
    {

        return values()
            .filter { it != this && this.invalidPrayerTypes.contains(it.type) || it.type == this.type }
            .toList()

    }

}