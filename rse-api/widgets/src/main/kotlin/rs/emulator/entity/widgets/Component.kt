package rs.emulator.entity.widgets

import io.reactivex.subjects.PublishSubject

/**
 *
 * @author javatar
 */

open class Component(val id: Int, var active: Boolean = false) {

    val events = PublishSubject.create<WidgetEvent>()

    companion object {

        val DEFAULT_COMPONENT = Component(-1)

    }

}