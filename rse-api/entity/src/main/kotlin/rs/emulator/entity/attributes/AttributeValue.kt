package rs.emulator.entity.attributes

import com.google.gson.Gson
import rs.emulator.utilities.koin.get

/**
 *
 * @author javatar
 */

abstract class AttributeValue {

    open fun serialize(): String {
        return get<Gson>().toJson(this)
    }

    abstract fun deserialize(serialized: String)

}