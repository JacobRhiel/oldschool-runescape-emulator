package rs.emulator.entity.material

enum class EquipmentSlot(val slot: Int) {

    HEAD(0),
    CAPE(1),
    WEAPON(3),
    CHEST(4),
    SHIELD(5),
    LEGS(7),
    HANDS(9),
    FEET(10),
    RING(12),
    ARROWS(13),
    NONE(-1);

    companion object {

        fun bySlot(slot : Int) : EquipmentSlot {
            for(es in values()) {
                if(es.slot == slot)
                    return es
            }
            return NONE
        }

    }

}