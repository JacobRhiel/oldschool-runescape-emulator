package rs.emulator.cache.definition.entity.obj.meta

import com.google.gson.reflect.TypeToken
import rs.emulator.cache.definition.generator.MetaDataDefinitionGenerator
import rs.emulator.definitions.impl.entity.obj.ObjMetaDataDefinition
import java.io.FileReader
import java.nio.file.Paths

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

        val resource = this::class.java.classLoader.getResource("data")

        val path = Paths.get(
            if (System.getProperty("os.name") == "Windows 10")
                resource.path.replace("%20", " ").replaceFirst("/", "")
            else resource.path
        )

        if(cached.isEmpty())

            cached = gson.fromJson(
                FileReader(path.resolve("osrsbox/item/items-complete.json").toFile()),
                object : TypeToken<HashMap<Int, ObjMetaDataDefinition>>(){}.type
            )

        return cached[id] ?: throw Error("Meta data not found for item $id")

    }

}