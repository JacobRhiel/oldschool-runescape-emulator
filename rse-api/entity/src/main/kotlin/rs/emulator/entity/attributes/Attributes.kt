package rs.emulator.entity.attributes

import io.reactivex.subjects.PublishSubject
import rs.emulator.entity.attributes.attribute.BooleanAttributeDelegate
import rs.emulator.entity.attributes.attribute.BooleanAttributeValue
import rs.emulator.entity.attributes.attribute.IntAttributeDelegate
import rs.emulator.entity.attributes.attribute.IntAttributeValue

/**
 *
 * @author javatar
 */

class Attributes(val attributes: MutableMap<String, String> = mutableMapOf()) {

    val valueChangeListener = PublishSubject.create<String>()

    operator fun set(key: String, value: AttributeValue) {
        attributes[key] = value.serialize()
    }

    operator fun get(key: String): String = attributes.getOrDefault(key, "")

    fun Int(init: Int = 0) = IntAttributeDelegate(this, IntAttributeValue(init))
    fun Boolean(init: Boolean = false) = BooleanAttributeDelegate(this, BooleanAttributeValue(init))

}