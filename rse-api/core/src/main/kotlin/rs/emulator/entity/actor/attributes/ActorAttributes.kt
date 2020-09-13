package rs.emulator.entity.actor.attributes

import io.reactivex.subjects.PublishSubject
import rs.emulator.entity.actor.attributes.attribute.BooleanAttributeDelegate
import rs.emulator.entity.actor.attributes.attribute.BooleanAttributeValue
import rs.emulator.entity.actor.attributes.attribute.IntAttributeDelegate
import rs.emulator.entity.actor.attributes.attribute.IntAttributeValue

/**
 *
 * @author javatar
 */

class ActorAttributes(val attributes: MutableMap<String, String> = mutableMapOf()) {

    val valueChangeListener = PublishSubject.create<String>()

    operator fun set(key: String, value: AttributeValue) {
        attributes[key] = value.serialize()
    }

    operator fun get(key: String): String = attributes.getOrDefault(key, "")

    fun Int(init: Int = 0) = IntAttributeDelegate(this, IntAttributeValue(init))
    fun Boolean(init: Boolean = false) = BooleanAttributeDelegate(this, BooleanAttributeValue(init))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ActorAttributes

        if (attributes != other.attributes) return false

        return true
    }

    override fun hashCode(): Int {
        return attributes.hashCode()
    }


}