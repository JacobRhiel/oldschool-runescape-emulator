package rs.emulator.definitions.entity.loc.delegates

import rs.emulator.definitions.entity.obj.ObjDefinition
import kotlin.reflect.KProperty

class NameDelegate {
    operator fun getValue(ref : ObjDefinition, property : KProperty<*>) : String {
        return ref.name
    }
}