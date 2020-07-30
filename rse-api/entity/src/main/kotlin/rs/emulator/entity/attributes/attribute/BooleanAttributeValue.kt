package rs.emulator.entity.attributes.attribute

import rs.emulator.entity.IEntity
import rs.emulator.entity.attributes.AttributeDelegate
import rs.emulator.entity.attributes.AttributeValue
import rs.emulator.entity.attributes.Attributes
import kotlin.reflect.KProperty

/**
 *
 * @author javatar
 */

class BooleanAttributeValue(var value: Boolean = false) : AttributeValue() {
    override fun serialize(): String {
        return "$value"
    }

    override fun deserialize(serialized: String) {
        value = serialized.toBoolean()
    }
}

class BooleanAttributeDelegate(attributes: Attributes, attribute: BooleanAttributeValue) :
    AttributeDelegate<BooleanAttributeValue, IEntity, Boolean>(
        attributes, attribute
    ) {
    override fun getValue(entity: IEntity, property: KProperty<*>): Boolean {
        if (persistent) {
            attribute.deserialize(attributes[property.name])
        }
        return attribute.value
    }

    override fun setValue(entity: IEntity, property: KProperty<*>, value: Boolean) {
        attribute.value = value
        changeListener.onNext(value)
        if (persistent) {
            attributes[property.name] = attribute
        }
    }
}