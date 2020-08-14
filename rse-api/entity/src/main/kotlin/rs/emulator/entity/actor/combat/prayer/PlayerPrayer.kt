package rs.emulator.entity.actor.combat.prayer

import rs.emulator.entity.actor.combat.prayer.PrayerType.*

/**
 *
 * @author Chk
 */
enum class PlayerPrayer(
    override val type: PrayerType,
    override val child: Int,
    override val drainPerMinute: Double,
    override vararg val invalidationPrayers: PrayerType = arrayOf(COMBAT)
) : IPrayer
{

    THICK_SKIN(DEFENCE, 5, 5.0),

    BURST_OF_STRENGTH(STRENGTH, 6, 5.0),

    CLARITY_OF_THOUGHT(ATTACK,7, 5.0),

    SHARP_EYE(RANGED, 23, 5.0),

    MYSTIC_WILL(MAGIC,24, 5.0),

    ROCK_SKIN(DEFENCE, 8, 10.0),

    SUPERHUMAN_STRENGTH(STRENGTH, 9, 10.0),

    IMPROVED_REFLEXES(ATTACK,10, 10.0),

    RAPID_RESTORE(RESTORATION, 11, 2.3, RESTORATION),

    RAPID_HEAL(RESTORATION, 12, 3.3, RESTORATION),

    PROTECT_ITEM(EFFECT,13, 3.0),

    HAWK_EYE(RANGED, 25, 10.0),

    MYSTIC_LORE(MAGIC, 26, 10.0),

    STEEL_SKIN(DEFENCE, 14, 20.0),

    ULTIMATE_STRENGTH(STRENGTH, 15, 20.0),

    INCREDIBLE_REFLEXES(ATTACK, 16, 20.0),

    PROTECT_FROM_MAGIC(PROTECTION, 17, 20.0),

    PROTECT_FROM_MISSILES(PROTECTION, 18, 20.0),

    PROTECT_FROM_MELEE(PROTECTION, 19, 20.0),

    EAGLE_EYE(RANGED,27, 20.0),

    MYSTIC_MIGHT(MAGIC,28, 20.0),

    RETRIBUTION(EFFECT, 20, 5.0),

    REDEMPTION(RESTORATION,21, 10.0),

    SMITE(EFFECT, 22, 40.0),

    PRESERVE(PRESERVATION, 33, 3.0),

    CHIVALRY(MELEE, 29, 40.0),

    PIETY(MELEE, 30, 40.0),

    RIGOUR(RANGED, 31, 40.0),

    AUGURY(MAGIC, 32, 40.0)

    ;

    override fun fetchInvalidationPrayers() : List<PlayerPrayer>
    {

        return values()
            .filter { it != this && invalidate(this, it) }
            .toList()

    }

    private fun invalidate(prayer: PlayerPrayer, otherPrayer: PlayerPrayer): Boolean
    {

        return if(prayer.type == COMBAT)
        {
            (otherPrayer.type == ATTACK
                    || otherPrayer.type == STRENGTH
                    || otherPrayer.type == DEFENCE
                    || otherPrayer.type == RANGED
                    || otherPrayer.type == MAGIC)
        }
        else (prayer.type == otherPrayer.type)

    }

}