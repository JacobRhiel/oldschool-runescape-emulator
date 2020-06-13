package rs.emulator.cache.store.index.archive

/**
 *
 * @author Chk
 */
enum class ArchiveConfig(
    val identifier: Int
)
{

    UNDERLAY(1),

    IDENTITY_KIT(3),

    OVERLAY(4),

    INV(5),

    LOC(6),

    ENUM(8),

    NPC(9),

    OBJ(10),

    PARAMS(11),

    SEQUENCE(12),

    SPOT_ANIM(13),

    VAR_BIT(14),

    VAR_CLIENT(19),

    VAR_CLIENT_STRING(15),

    VAR_PLAYER(16),

    HIT_SPLAT(32),

    STRUCT(34),

    AREA(35)

    ;

    companion object
    {

        fun fetchArchive(identifier: Int) = values().firstOrNull { it.identifier == identifier }

    }

}