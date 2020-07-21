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

    PUBLIC_CHAT(1),

    ANIMATION(128),

    GRAPHIC(4096),

    FORCE_TEXT(2),

    FACE_COORDINATE(64),

    FACE_ENTITY(8),

    CONTEXT_MENU(1024),

    FORCE_MOVEMENT(512),

    HIT_MARK(4)

    //todo: 2048?

    ;



}