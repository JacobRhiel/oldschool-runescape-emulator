package rs.emulator.cache.definition

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.AbstractDefinitionRepository
import rs.emulator.cache.definition.entity.loc.LocDefinitionGenerator
import rs.emulator.cache.definition.entity.npc.NpcDefinitionGenerator
import rs.emulator.cache.definition.entity.obj.ObjDefinitionGenerator
import rs.emulator.cache.definition.entity.obj.meta.ObjMetaDataDefinitionGenerator
import rs.emulator.cache.definition.entity.sequence.SequenceDefinitionGenerator
import rs.emulator.cache.definition.entity.spotanim.SpotAnimDefinitionGenerator
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
import java.util.concurrent.TimeUnit

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
        StructDefinitionGenerator()
    )

    @PublishedApi internal val definitionCache: Cache<Class<Definition>, HashMap<Int, Definition>> = Caffeine.newBuilder()
        .maximumSize(255) //65535 default maximum size of any entry.
        .expireAfterAccess(2, TimeUnit.MINUTES)
        .recordStats()
        .build()

    private fun submitType(clazz: Class<Definition>) = definitionCache.put(clazz, hashMapOf())

    override fun findActual(identifier: Int, child: Int, keys: IntArray?, clazz : Class<*>): Definition?
    {

        val generator = generators.firstOrNull { it.definitionClass == clazz }
            ?: throw Error("No generator found for definition type: ${clazz.simpleName}.")

        val shiftedId = generator.shiftedId

        val hasShiftedId = shiftedId != -1

        println("test1 ${clazz.simpleName}")

        val reader = when
        {

            generator.namedArchive ->
            {

                val archiveName = generator.generateArchiveName(identifier)

                val foundArchive = fileStore.fetchIndex(generator.indexConfig.identifier).fetchNamedArchive(archiveName)!!

                foundArchive.fetchBuffer(true, keys)

            }
            else                   ->
            {

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

        val definition = generator.decodeHeader(identifier, reader)

        submitEntry(definition)

        val definitions = definitionCache.getIfPresent(clazz)!!

        return definitions[identifier]

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