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

class IntAttributeValue(init: Int) : AttributeValue() {

    var value: Int = init

    override fun serialize(): String {
        return "$value"
    }

    override fun deserialize(serialized: String) {
        value = serialized.toInt()
    }
}

class IntAttributeDelegate(attributes: Attributes, attribute: IntAttributeValue) :
    AttributeDelegate<IntAttributeValue, IEntity, Int>(attributes, attribute) {
    override fun getValue(entity: IEntity, property: KProperty<*>): Int {
        if (persistent) {
            attribute.deserialize(attributes[property.name])
        }
        return attribute.value
    }

    override fun setValue(entity: IEntity, property: KProperty<*>, value: Int) {
        attribute.value = value
        changeListener.onNext(value)
        if (persistent) {
            attributes[property.name] = attribute
        }
    }
}