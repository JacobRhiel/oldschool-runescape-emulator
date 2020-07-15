package rs.emulator.plugins.extensions.factories.actions

/**
 *
 * @author javatar
 */

interface ItemGroundAction {

    fun handleItemGroundAction(
        itemId: Int,
        option: Int
    )

}