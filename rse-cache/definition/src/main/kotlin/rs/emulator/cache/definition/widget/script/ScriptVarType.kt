package rs.emulator.cache.definition.widget.script

/**
 *
 * @author Chk
 */
enum class ScriptVarType(val keyChar: Char, val fullName: String)
{

    INTEGER('i', "integer"),

    BOOLEAN('1', "boolean"),

    SEQ('A', "seq"),

    COLOUR('C', "colour"),

    /**
     * Also known as {@code Widget}.
     */
    COMPONENT('I', "component"),

    IDKIT('K', "idkit"),

    MIDI('M', "midi"),

    SYNTH('P', "synth"),

    STAT('S', "stat"),

    COORDGRID('c', "coordgrid"),

    GRAPHIC('d', "graphic"),

    FONTMETRICS('f', "fontmetrics"),

    ENUM('g', "enum"),

    JINGLE('j', "jingle"),

    /**
     * Also known as {@code Object}.
     */
    LOC('l', "loc"),

    MODEL('m', "model"),

    NPC('n', "npc"),

    /**
     * Also known as {@code Item}.
     */
    OBJ('o', "obj"),

    /**
     * Another version of {@code OBJ}, but means that on Jagex's side they used the internal name for an item.
     */
    NAMEDOBJ('O', "namedobj"),

    STRING('s', "string"),

    SPOTANIM('t', "spotanim"),

    INV('v', "inv"),

    TEXTURE('x', "texture"),

    CHAR('z', "char"),

    MAPSCENEICON('£', "mapsceneicon"),

    MAPELEMENT('µ', "mapelement"),

    HITMARK('×', "hitmark"),

    STRUCT('J', "struct")

    ;

    companion object
    {

        fun forCharKey(key: Char): ScriptVarType = values().first { it.keyChar == key }

    }

}