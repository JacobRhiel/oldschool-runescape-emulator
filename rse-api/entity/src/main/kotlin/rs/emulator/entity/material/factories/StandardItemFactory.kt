package rs.emulator.entity.material.factories

import rs.emulator.entity.material.AbstractMaterialFactory
import rs.emulator.entity.material.items.StandardItem

/**
 *
 * @author javatar
 */

class StandardItemFactory : AbstractMaterialFactory<StandardItem> {
    override fun create(id: Int, amount: Int, stackable: Boolean): StandardItem {
        return StandardItem(id, amount, stackable)
    }

    override fun create(block: StandardItem.() -> Unit): StandardItem {
        val item = StandardItem(-1)
        block(item)
        return item
    }
}