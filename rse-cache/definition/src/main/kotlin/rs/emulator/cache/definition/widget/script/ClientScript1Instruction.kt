package rs.emulator.cache.definition.widget.script

/**
 *
 * @author Chk
 */
data class ClientScript1Instruction(var opcode: Opcode = Opcode.RETURN, var operands: IntArray = intArrayOf())
{

    enum class Opcode(val argumentCount: Int = 0)
    {

        RETURN(0),

        BOOSTED_SKILL_LEVELS(1),

        REAL_SKILL_LEVELS(1),

        SKILL_EXPERIENCE(1),

        WIDGET_CONTAINS_ITEM_GET_QUANTITY(3),

        VARP(1),

        EXPERIENCE_AT_LEVEL_FOR_SKILL(1),

        VARP_TIMES_469(1),

        COMBAT_LEVEL(1),

        TOTAL_LEVEL(0),

        WIDGET_CONTAINS_ITEM_STAR(3),

        RUN_ENERGY(0),

        WEIGHT(0),

        VARP_TESTBIT(2),

        VARBIT(1),

        MINUS(0),

        DIV(0),

        MUL(0),

        WORLD_X(0),

        WORLD_Y(1),

        CONSTANT(1)

        ;

    }

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ClientScript1Instruction

        if (opcode != other.opcode) return false
        if (!operands.contentEquals(other.operands)) return false

        return true
    }

    override fun hashCode(): Int
    {
        var result = opcode.hashCode()
        result = 31 * result + operands.contentHashCode()
        return result
    }

}