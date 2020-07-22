package rs.dusk.engine.model.entity

/**
 *
 * @author Chk
 */
data class Size(val width: Int, val height: Int)
{

    companion object
    {

        val TILE = Size(1, 1)

    }

}