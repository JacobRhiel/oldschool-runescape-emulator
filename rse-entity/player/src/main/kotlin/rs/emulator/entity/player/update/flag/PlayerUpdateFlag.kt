package rs.emulator.entity.player.update.flag

import rs.emulator.entity.update.flag.UpdateFlag

/**
 *
 * @author Chk
 */
enum class PlayerUpdateFlag(override val bit: Int) : UpdateFlag
{

    APPEARANCE(32),

    MOVEMENT(256),

    PUBLIC_CHAT(1)

    ;



}