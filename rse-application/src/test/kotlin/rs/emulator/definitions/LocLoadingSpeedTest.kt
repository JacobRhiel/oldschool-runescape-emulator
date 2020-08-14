package rs.emulator.definitions

import org.koin.core.context.startKoin
import org.koin.dsl.module
import rs.emulator.cache.definition.DefinitionRepository
import rs.emulator.cache.store.VirtualFileStore
import rs.emulator.cache.store.data.DataFile
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.cache.store.index.archive.ArchiveConfig
import rs.emulator.cache.store.reference.ReferenceTable
import rs.emulator.definitions.entity.loc.LocDefinition
import rs.emulator.encryption.xtea.XteaKeyService
import rs.emulator.fileserver.FileStoreService
import rs.emulator.utilities.koin.get
import java.nio.file.Paths

/**
 *
 * @author javatar
 */

class LocLoadingSpeedTest {

    fun speedTest() {

        val path = Paths.get("/home/Javatar/IdeaProjects/oldschool-runescape-emulator/data/cache")

        val modules = module {
            single { DataFile(path.resolve("main_file_cache.dat2")) }

            single { ReferenceTable(path.resolve("main_file_cache.idx255")) }

            single { VirtualFileStore(path) }

            single { DefinitionRepository() }

            single { FileStoreService() }

            //single { XteaKeyService() }
        }

        startKoin {
            modules(modules)
        }

        val fileStore: VirtualFileStore = get()

        fileStore.preload()

        val locCount =
            fileStore.fetchIndex(IndexConfig.CONFIGS.identifier).fetchArchive(ArchiveConfig.LOC.identifier).table.count

        println("loc count: $locCount")

        val repo: DefinitionRepository = get()

        val locs = repo.cacheConfigDefinitions<LocDefinition>()

        println(locs.size)

    }

}