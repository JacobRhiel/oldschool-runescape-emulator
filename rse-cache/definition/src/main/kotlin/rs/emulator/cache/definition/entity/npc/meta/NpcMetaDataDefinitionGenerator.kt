package rs.emulator.cache.definition.entity.npc.meta

import com.google.gson.reflect.TypeToken
import rs.emulator.cache.definition.generator.MetaDataDefinitionGenerator
import rs.emulator.definitions.impl.entity.npc.NpcMetaDataDefinition
import rs.emulator.definitions.impl.entity.obj.ObjMetaDataDefinition
import java.io.File
import java.io.FileReader

/**
 *
 * @author Chk
 */
class NpcMetaDataDefinitionGenerator: MetaDataDefinitionGenerator<NpcMetaDataDefinition>()
{

    override val definitionClass: Class<NpcMetaDataDefinition> = NpcMetaDataDefinition::class.java

    override fun decodeHeader(id: Int): NpcMetaDataDefinition = generate(id)

    override fun generate(id: Int): NpcMetaDataDefinition
    {

        val map : MutableMap<Int, NpcMetaDataDefinition> = gson.fromJson(
            FileReader(File("./data/osrsbox/monster/monsters-complete.json")),
            object : TypeToken<HashMap<Int, NpcMetaDataDefinition>>(){}.type
        )

        return map[id]!!

    }

}