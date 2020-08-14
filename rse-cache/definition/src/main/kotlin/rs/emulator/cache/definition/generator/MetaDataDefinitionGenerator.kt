package rs.emulator.cache.definition.generator

import com.google.gson.Gson
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.definitions.MetaDataDefinition
import rs.emulator.definitions.entity.obj.ObjMetaDataDefinition
import rs.emulator.utilities.koin.get

/**
 *
 * @author Chk
 */
abstract class MetaDataDefinitionGenerator<T : MetaDataDefinition> : DefinitionGenerator<T>()
{

    val gson: Gson = get()

    var cached: MutableMap<Int, ObjMetaDataDefinition> = mutableMapOf()

    override val archive: Int = -1

    override val indexConfig: IndexConfig
        get() = TODO("Not yet implemented")

    override val namedArchive: Boolean = false

    override val shiftedId: Int = -1

    abstract fun decodeHeader(id: Int) : T

    override fun decodeHeader(id: Int, reader: BufferedReader): T = decodeHeader(id)

    abstract fun generate(id: Int) : T

    override fun generate(id: Int, reader: BufferedReader): T = generate(id)

}