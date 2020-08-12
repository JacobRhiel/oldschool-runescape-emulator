import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import rs.emulator.Repository
import rs.emulator.cache.definition.DefinitionRepository
import rs.emulator.cache.definition.definition
import rs.emulator.cache.store.VirtualFileStore
import rs.emulator.cache.store.data.DataFile
import rs.emulator.cache.store.reference.ReferenceTable
import rs.emulator.encryption.xtea.XteaKeyService
import rs.emulator.entity.material.items.StandardItem
import rs.emulator.utilities.koin.get
import java.nio.file.Paths

class BankTest {

    companion object {

        fun setup() {
            val path = Paths.get("/home/Javatar/IdeaProjects/oldschool-runescape-emulator/data/cache")
            startKoin {
                modules(module {
                    single { DataFile(path.resolve("main_file_cache.dat2")) }

                    single { ReferenceTable(path.resolve("main_file_cache.idx255")) }

                    single { VirtualFileStore(path) }

                    single { XteaKeyService() }

                    single { DefinitionRepository() }

                    single { Gson() }
                })
            }
            val fileStore = get<VirtualFileStore>()
            fileStore.preload()
            Repository.definitionRepository = definition()
        }
    }

    //@Test
    fun addTest() {


    }

    @Test
    fun placeholderAdd() {


    }

    @Test
    fun attrTest() {


    }

}