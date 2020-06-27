package rs.emulator.cache.definition.entity.obj.meta

import com.google.gson.Gson
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.definition.generator.DefinitionGenerator
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.cache.store.index.archive.ArchiveConfig
import rs.emulator.definitions.entity.obj.ObjMetaDataDefinition

/**
 *
 * @author Chk
 */
class ObjMetaDataDefinitionGenerator : DefinitionGenerator<ObjMetaDataDefinition>()
{

    override val definitionClass: Class<ObjMetaDataDefinition> = ObjMetaDataDefinition::class.java

    override val indexConfig: IndexConfig = IndexConfig.META_DATA

    override val archive: Int = ArchiveConfig.OBJ.identifier

    override fun decodeHeader(id: Int, reader: BufferedReader): ObjMetaDataDefinition = generate(id, reader)

    override fun generate(id: Int, reader: BufferedReader): ObjMetaDataDefinition = Gson().fromJson(String(reader.toArray()), ObjMetaDataDefinition::class.java)

}