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

class IntAttributeValue(private val init: Int) : AttributeValue() {

    var value: Int = init

    override fun serialize(): String {
        return "$value"
    }

    override fun deserialize(serialized: String) {
        value = if (serialized.isNotEmpty()) {
            serialized.toInt()
        } else {
            init
        }
    }
}

class IntAttributeDelegate(actorAttributes: ActorAttributes, attribute: IntAttributeValue) :
    AttributeDelegate<IntAttributeValue, IEntity, Int>(actorAttributes, attribute) {
    override fun getValue(entity: IEntity, property: KProperty<*>): Int {
        if (persistent) {
            attribute.deserialize(actorAttributes[property.name])
        }
        return attribute.value
    }

    override fun setValue(entity: IEntity, property: KProperty<*>, value: Int) {
        attribute.value = value
        changeListener.onNext(value)
        if (persistent) {
            actorAttributes[property.name] = attribute
        }
    }
}