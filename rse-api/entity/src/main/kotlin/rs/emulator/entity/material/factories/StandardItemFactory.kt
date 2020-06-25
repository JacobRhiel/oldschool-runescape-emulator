package rs.emulator.entity.material.factories

import rs.emulator.entity.material.AbstractMaterialFactory
import rs.emulator.entity.material.items.StandardItem

/**
 *
 * @author javatar
 */

class StandardItemFactory : AbstractMaterialFactory<StandardItem> {

    val cacheMap = mutableMapOf<Int, StandardItem>()

    override fun create(id: Int, amount: Int, stackable: Boolean): StandardItem {
        if(cacheMap.containsKey(id)) {
            return cacheMap[id]!!.copy()
        }
        return StandardItem(id, amount, stackable)
    }

    override fun create(cache: Boolean, block: StandardItem.() -> Unit): StandardItem {
        val item = StandardItem(-1)
        block(item)
        if(cache && item.id != -1) {
            cacheMap[item.id] = item.copy()
        }
        return item
    }
}