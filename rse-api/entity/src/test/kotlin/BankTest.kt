import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.junit.jupiter.api.BeforeAll
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
import rs.emulator.entity.material.containers.impl.Bank
import rs.emulator.entity.material.items.StandardItem
import rs.emulator.utilities.koin.get
import java.nio.file.Paths

class BankTest {

    companion object {
        @BeforeAll
        @JvmStatic
        fun setup() {
            val path = Paths.get("/home/Javatar/IdeaProjects/oldschool-runescape-emulator/data/cache")
            startKoin {
                modules(module {
                    single { DataFile(path.resolve("main_file_cache.dat2")) }

                    single { ReferenceTable(path.resolve("main_file_cache.idx255")) }

                    single { VirtualFileStore(path) }

                    single { XteaKeyService() }

                    single { DefinitionRepository() }
                })
            }
            val fileStore = get<VirtualFileStore>()
            fileStore.preload()
            Repository.definitionRepository = definition()
        }
    }

    @Test
    fun addTest() {

        val bank = Bank()

        bank.add(StandardItem(995, 2147000000, true))
            .onEach { println("$it") }
            .launchIn(CoroutineScope(Dispatchers.Unconfined))

        assert(StandardItem(995, 2147000000, true) in bank)

        bank.add(StandardItem(995, 10000000, true))
            .onEach {
                println(it)
                assert(it.item.amount == 483647)
            }
            .launchIn(CoroutineScope(Dispatchers.Unconfined))

        assert(StandardItem(995, Integer.MAX_VALUE, true) in bank)

    }

}