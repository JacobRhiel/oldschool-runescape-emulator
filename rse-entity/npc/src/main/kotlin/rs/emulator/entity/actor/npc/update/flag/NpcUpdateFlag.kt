package rs.emulator.entity.actor.npc.update.flag

import rs.emulator.entity.update.flag.UpdateFlag

/**
 *
 * @author Chk
 */
enum class NpcUpdateFlag(override val bit: Int) : UpdateFlag
{

    APPEARANCE(4),

}