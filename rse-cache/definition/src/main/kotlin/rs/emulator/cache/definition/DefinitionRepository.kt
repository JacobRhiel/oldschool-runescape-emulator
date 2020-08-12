package rs.emulator.cache.definition

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.AbstractDefinitionRepository
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.definition.entity.idk.IdentityKitDefinitionGenerator
import rs.emulator.cache.definition.entity.loc.LocDefinitionGenerator
import rs.emulator.cache.definition.entity.npc.NpcDefinitionGenerator
import rs.emulator.cache.definition.entity.npc.meta.NpcMetaDataDefinitionGenerator
import rs.emulator.cache.definition.entity.obj.ObjDefinitionGenerator
import rs.emulator.cache.definition.entity.obj.meta.ObjMetaDataDefinitionGenerator
import rs.emulator.cache.definition.entity.sequence.SequenceDefinitionGenerator
import rs.emulator.cache.definition.entity.spotanim.SpotAnimDefinitionGenerator
import rs.emulator.cache.definition.generator.DefinitionGenerator
import rs.emulator.cache.definition.generator.MetaDataDefinitionGenerator
import rs.emulator.cache.definition.region.landscape.LandscapeDefinitionGenerator
import rs.emulator.cache.definition.region.mapscape.MapScapeDefinitionGenerator
import rs.emulator.cache.definition.varp.bit.VarBitDefinitionGenerator
import rs.emulator.cache.definition.varp.player.VarPlayerDefinitionGenerator
import rs.emulator.cache.definition.widget.WidgetDefinitionGenerator
import rs.emulator.cache.definition.widget.enum.EnumDefinitionGenerator
import rs.emulator.cache.definition.widget.inv.InvDefinitionGenerator
import rs.emulator.cache.definition.widget.param.ParamDefinitionGenerator
import rs.emulator.cache.definition.widget.script.ScriptDefinitionGenerator
import rs.emulator.cache.definition.widget.struct.StructDefinitionGenerator
import rs.emulator.cache.store.VirtualFileStore
import rs.emulator.definitions.Definition
import rs.emulator.definitions.widget.WidgetDefinition
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

/**
 *
 * @author Chk
 */
class DefinitionRepository : KoinComponent, AbstractDefinitionRepository()
{

    @PublishedApi internal val fileStore: VirtualFileStore = get()

    @PublishedApi internal val generators = listOf(
        ObjDefinitionGenerator(),
        ObjMetaDataDefinitionGenerator(),
        NpcDefinitionGenerator(),
        LocDefinitionGenerator(),
        VarBitDefinitionGenerator(),
        VarPlayerDefinitionGenerator(),
        LandscapeDefinitionGenerator(),
        MapScapeDefinitionGenerator(),
        InvDefinitionGenerator(),
        ParamDefinitionGenerator(),
        WidgetDefinitionGenerator(),
        SequenceDefinitionGenerator(),
        SpotAnimDefinitionGenerator(),
        EnumDefinitionGenerator(),
        ScriptDefinitionGenerator(),
        StructDefinitionGenerator(),
        IdentityKitDefinitionGenerator(),
        ObjMetaDataDefinitionGenerator(),
        NpcMetaDataDefinitionGenerator()
    )

    @PublishedApi
    internal val definitionCache: Cache<Class<Definition>, HashMap<Int, Definition>> = Caffeine.newBuilder()
        .maximumSize(255) //65535 default maximum size of any entry.
        .expireAfterAccess(2, TimeUnit.MINUTES)
        .recordStats()
        .build()

    @PublishedApi
    internal val widgetCache: Cache<Int, Array<WidgetDefinition>> = Caffeine.newBuilder()
        .maximumSize(255)
        .expireAfterAccess(2, TimeUnit.MINUTES)
        .recordStats()
        .build()

    private fun submitType(clazz: Class<Definition>) = definitionCache.put(clazz, hashMapOf())

    inline fun <reified D : Definition> cacheConfigDefinitions(): List<D> {
        val generator = generators.firstOrNull { it.definitionClass == D::class.java }
        if (generator != null) {

            val index = fileStore.fetchIndex(generator.indexConfig.identifier)
            val archive = index.fetchArchive(generator.archive)

            val size = archive.table.count

            println("Size : $size")

            return IntRange(0, size - 1).toList().stream()
                .map {
                    generator.decodeHeader(
                        it,
                        archive.fetchEntry(it).fetchBuffer(true)
                    ) as D
                }.collect(Collectors.toList())
        }
        return listOf()
    }

    override fun findActual(identifier: Int, child: Int, keys: IntArray?, clazz: Class<*>): Definition? {

        val generator = generators.firstOrNull { it.definitionClass == clazz }
            ?: throw Error("No generator found for definition type: ${clazz.simpleName}.")

        val shiftedId = generator.shiftedId

        val hasShiftedId = shiftedId != -1

        var reader: BufferedReader? = null

        if(generator !is MetaDataDefinitionGenerator)
        {

            reader = when
            {

                generator.namedArchive -> {

                    val archiveName = generator.generateArchiveName(identifier)

                    val foundArchive =
                        fileStore.fetchIndex(generator.indexConfig.identifier).fetchNamedArchive(archiveName)!!

                    foundArchive.fetchBuffer(true, keys)

                }
                else -> {

                    fileStore.fetchIndex(generator.indexConfig.identifier)
                        .fetchArchive(
                            if (child == -1)
                                if (generator.archive == -1)
                                    identifier
                                else
                                    generator.archive
                            else if (hasShiftedId)
                                shiftedId
                            else identifier
                        )
                        .fetchEntry(
                            if (child == -1)
                                if (hasShiftedId)
                                    shiftedId
                                else
                                    identifier
                            else
                                child
                        ).fetchBuffer(true)
                }
            }

        }

        //todo? check if reader is null?
        val definition = if(generator is MetaDataDefinitionGenerator) generator.decodeHeader(identifier) else generator.decodeHeader(identifier, reader!!)

        submitEntry(definition)

        val definitions = definitionCache.getIfPresent(clazz)!!

        return definitions[identifier]

    }

    override fun findWidget(identifier: Int): Array<WidgetDefinition> {
        val gen: DefinitionGenerator<out Definition>? =
            generators.find { it.definitionClass == WidgetDefinition::class.java }
        val widgetChildren: Array<WidgetDefinition>? = widgetCache.getIfPresent(identifier)
        if (gen != null && widgetChildren == null) {
            val archive = fileStore.fetchIndex(gen.indexConfig.identifier).fetchArchive(identifier)
            val childrenSize = archive.entryCount
            val children = mutableListOf<WidgetDefinition>()
            repeat(childrenSize) {
                val data = archive.fetchEntry(it)
                children.add(gen.decodeHeader(it, data.fetchBuffer(true)) as WidgetDefinition)
            }
            widgetCache.put(identifier, children.toTypedArray())
            return widgetCache.getIfPresent(identifier) ?: arrayOf()
        }
        return widgetChildren ?: arrayOf()
    }

    fun submitEntry(definition: Definition) {

        var cache = definitionCache.getIfPresent(definition.javaClass)

        if (cache == null)
            submitType(definition.javaClass)

        cache = definitionCache.getIfPresent(definition.javaClass)!!

        cache[definition.id] = definition

    }

    fun expireCacheFor(definition: Definition)
    {

        definitionCache.cleanUp()

        definitionCache.invalidate(definition)

    }

    fun expireCache()
    {

        definitionCache.cleanUp()

        definitionCache.invalidateAll()

    }

}