package rs.emulator.entity.material.items

import rs.emulator.definitions.factories.ItemDefinitionFactory

/**
 *
 * @author javatar
 */

class StandardItem(id: Int, amount: Int = 1, stackable: Boolean = false) : Item(id, amount, stackable) {
    override fun copy(amount: Int, stackable: Boolean): StandardItem {
        return StandardItem(this.id, amount, stackable)
            .also { it.attributes.setAttributes(this.attributes.map) }
    }

    override fun toNoted(): StandardItem {
        val def = ItemDefinitionFactory.provide(this.id)
        return if (def.noteTemplateId == 0 && def.noteLinkId > 0) StandardItem(
            def.noteLinkId,
            amount,
            stackable
        ) else this.copy()
    }

    override fun toUnnoted(): StandardItem {
        val def = ItemDefinitionFactory.provide(this.id)
        return if (def.noteTemplateId > 0) StandardItem(def.noteLinkId, amount, stackable) else this.copy()
    }
}