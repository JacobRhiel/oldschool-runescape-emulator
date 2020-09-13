package rs.emulator.entity.actor.attributes

import io.reactivex.subjects.PublishSubject
import rs.emulator.entity.IEntity
import kotlin.reflect.KProperty

/**
 *
 * @author javatar
 */

abstract class AttributeDelegate<Attribute : AttributeValue, Entity : IEntity, V>(
    val actorAttributes: ActorAttributes,
    val attribute: Attribute,
    persistent: Boolean = false
) {

    var persistent: Boolean = persistent
        private set(value) {
            field = value
        }

    val changeListener = PublishSubject.create<V>()

    fun markPersistent() = apply { persistent = true }

    abstract operator fun getValue(entity: Entity, property: KProperty<*>): V

    abstract operator fun setValue(entity: Entity, property: KProperty<*>, value: V)

}