package rs.emulator.definitions.impl.entity.loc.delegates

import rs.emulator.definitions.impl.entity.obj.ObjDefinition
import kotlin.reflect.KProperty

class NameDelegate {
    operator fun getValue(ref : ObjDefinition, property : KProperty<*>) : String {
        return ref.name
    }
}