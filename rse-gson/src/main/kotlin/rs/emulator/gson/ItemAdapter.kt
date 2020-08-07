package rs.emulator.gson

import com.google.gson.*
import rs.emulator.entity.material.ItemData
import rs.emulator.entity.material.items.Item
import rs.emulator.entity.material.items.StandardItem
import rs.emulator.entity.material.items.Wearable
import java.lang.reflect.Type

/**
 *
 * @author javatar
 */

class ItemAdapter : JsonDeserializer<Item>, JsonSerializer<Item> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Item {
        val obj = json.asJsonObject
        val name = obj.getAsJsonPrimitive("item_type").asString
        val clazz = Class.forName(name)

        val itemObj = obj.get("item_data").asJsonObject
        val id = itemObj.get("id").asInt
        if (id == -1 && clazz == StandardItem::class.java) {
            return ItemData.EMPTY
        } else if (id == -1 && clazz == Wearable::class.java) {
            return ItemData.EMPTY_WEARABLE
        }
        return context.deserialize(obj.get("item_data"), clazz)
    }

    override fun serialize(src: Item, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val name = src::class.java.name
        val obj = JsonObject()
        obj.addProperty("item_type", name)
        val element = context.serialize(src)
        obj.add("item_data", element)
        return obj
    }
}