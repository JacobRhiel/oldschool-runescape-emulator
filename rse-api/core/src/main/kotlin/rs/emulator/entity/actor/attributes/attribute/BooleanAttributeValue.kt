package rs.emulator.entity.actor.attributes.attribute

import rs.emulator.entity.IEntity
import rs.emulator.entity.actor.attributes.ActorAttributes
import rs.emulator.entity.actor.attributes.AttributeDelegate
import rs.emulator.entity.actor.attributes.AttributeValue
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
        value = if (serialized.isNotEmpty()) {
            serialized.toBoolean()
        } else {
            false
        }
    }
}

class BooleanAttributeDelegate(actorAttributes: ActorAttributes, attribute: BooleanAttributeValue) :
    AttributeDelegate<BooleanAttributeValue, IEntity, Boolean>(
        actorAttributes, attribute
    ) {
    override fun getValue(entity: IEntity, property: KProperty<*>): Boolean {
        if (persistent) {
            attribute.deserialize(actorAttributes[property.name])
        }
        return attribute.value
    }

    override fun setValue(entity: IEntity, property: KProperty<*>, value: Boolean) {
        attribute.value = value
        changeListener.onNext(value)
        if (persistent) {
            actorAttributes[property.name] = attribute
        }
    }
}