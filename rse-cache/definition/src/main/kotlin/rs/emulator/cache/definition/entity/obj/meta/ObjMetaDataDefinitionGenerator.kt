package rs.emulator.cache.definition.entity.obj.meta

import com.google.gson.reflect.TypeToken
import rs.emulator.cache.definition.generator.MetaDataDefinitionGenerator
import rs.emulator.definitions.entity.obj.ObjMetaDataDefinition
import java.io.File
import java.io.FileReader

/**
 *
 * @author Chk
 */
class ObjMetaDataDefinitionGenerator : MetaDataDefinitionGenerator<ObjMetaDataDefinition>()
{

    override val definitionClass: Class<ObjMetaDataDefinition> = ObjMetaDataDefinition::class.java

    override fun decodeHeader(id: Int): ObjMetaDataDefinition = generate(id)

    override fun generate(id: Int): ObjMetaDataDefinition
    {

        val map : MutableMap<Int, ObjMetaDataDefinition> = gson.fromJson(
            FileReader(File("./data/osrsbox/item/items-complete.json")),
            object : TypeToken<HashMap<Int, ObjMetaDataDefinition>>(){}.type
        )

        return map[id] ?: throw Error("Meta data not found for item $id")

    }

}