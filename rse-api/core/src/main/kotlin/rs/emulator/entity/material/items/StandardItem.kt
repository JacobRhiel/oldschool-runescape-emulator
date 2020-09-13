package rs.emulator.entity.material.items

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.definitions.impl.factories.ItemDefinitionFactory

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
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
        ).also { it.attributes.setAttributes(this.attributes.map) } else this.copy()
    }

    override fun toUnnoted(): StandardItem {
        val def = ItemDefinitionFactory.provide(this.id)
        return if (def.noteTemplateId > 0) StandardItem(
            def.noteLinkId,
            amount,
            stackable
        ).also { it.attributes.setAttributes(this.attributes.map) } else this.copy()
    }

    override fun toPlaceholder(): StandardItem {
        val def = ItemDefinitionFactory.provide(this.id)
        return if (def.placeholderTemplate == 0 && def.placeholderLink > 0) {
            StandardItem(def.placeholderLink, amount, stackable)
                .also { it.attributes.setAttributes(this.attributes.map) }
        } else {
            this.copy()
        }
    }

    override fun fromPlaceholder(): StandardItem {
        val def = ItemDefinitionFactory.provide(this.id)
        return if (def.placeholderTemplate > 0) StandardItem(
            def.placeholderLink,
            amount,
            stackable
        ).also { this.attributes.setAttributes(this.attributes.map) } else this.copy()
    }
}