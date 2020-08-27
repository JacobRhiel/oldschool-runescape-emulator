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

        if(cached.isEmpty())

            cached = gson.fromJson(
                FileReader(File("/home/Javatar/IdeaProjects/oldschool-runescape-emulator/rse-application/src/main/resources/data/osrsbox/item/items-complete.json")),
                object : TypeToken<HashMap<Int, ObjMetaDataDefinition>>(){}.type
            )

        return cached[id] ?: throw Error("Meta data not found for item $id")

    }

}