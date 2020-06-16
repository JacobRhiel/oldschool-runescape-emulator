package rs.emulator.cache.definition

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.Repository
import rs.emulator.cache.definition.entity.loc.LocDefinitionGenerator
import rs.emulator.cache.definition.entity.npc.NpcDefinitionGenerator
import rs.emulator.cache.definition.entity.obj.ObjDefinitionGenerator
import rs.emulator.cache.definition.entity.obj.meta.ObjMetaDataDefinitionGenerator
import rs.emulator.cache.definition.region.landscape.LandscapeDefinitionGenerator
import rs.emulator.cache.definition.region.mapscape.MapScapeDefinitionGenerator
import rs.emulator.cache.definition.varp.bit.VarBitDefinitionGenerator
import rs.emulator.cache.definition.varp.player.VarPlayerDefinitionGenerator
import rs.emulator.cache.definition.widget.WidgetDefinitionGenerator
import rs.emulator.cache.definition.widget.inv.InvDefinitionGenerator
import rs.emulator.cache.definition.widget.param.ParamDefinitionGenerator
import rs.emulator.cache.store.VirtualFileStore
import java.util.concurrent.TimeUnit

/**
 *
 * @author Chk
 */
class DefinitionRepository : KoinComponent, Repository
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
        WidgetDefinitionGenerator()
    )

    @PublishedApi internal val definitionCache: Cache<Class<Definition>, HashMap<Int, Definition>> = Caffeine.newBuilder()
        .maximumSize(0xFFFF) //65535 default maximum size of any entry.
        .expireAfterAccess(2, TimeUnit.MINUTES)
        .recordStats()
        .build()

    private fun submitType(clazz: Class<Definition>) = definitionCache.put(clazz, hashMapOf())

    override fun <T : rs.emulator.Definition> findDefinition(identifier: Int, child: Int): T {
        return find(identifier, child)
    }

    inline fun <reified T : Definition> find(identifier: Int): T = find(identifier, -1)

    inline fun <reified T : Definition> find(identifier: Int, child: Int): T
    {

        val generator = generators.firstOrNull { it.definitionClass == T::class.java }
            ?: throw Error("No generator found for definition type: ${T::class.simpleName}.")

        val shiftedId = generator.shiftedId

        val hasShiftedId = shiftedId != -1

        println("test1")

        val reader = when
        {
            generator.namedArchive ->
            {

                println("lol2")

                val archiveName = generator.generateArchiveName(identifier)

                //todo: null?
                fileStore.fetchIndex(generator.indexConfig.identifier).fetchNamedArchive(archiveName)?.decompressedBuffer?.copy()!!

            }
            else                   ->
            {

                println("test")

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
                    ).decompressedBuffer.copy()
            }
        }

        println("abc")

        val definition = generator.decodeHeader(identifier, reader)

        submitEntry(definition)

        val definitions = definitionCache.getIfPresent(T::class.java)!!

        return definitions[identifier] as T

    }

    fun submitEntry(definition: Definition)
    {

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