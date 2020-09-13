import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import rs.emulator.collections.varbits.VarbitList
import rs.emulator.definitions.impl.factories.ItemDefinitionFactory
import rs.emulator.entity.material.containers.events.impl.StackedContainerEvent
import rs.emulator.entity.material.containers.impl.bank.Bank
import rs.emulator.entity.material.containers.onEachEvent
import rs.emulator.entity.material.items.Item
import rs.emulator.entity.material.items.StandardItem
import rs.emulator.definitions.data.VarBits
import rs.emulator.reactive.test
import rs.emulator.utilities.contexts.scopes.ActorScope
import rs.emulator.utilities.koin.get
import java.nio.file.Paths

@ExperimentalCoroutinesApi
class BankTest {

    companion object {
        @BeforeAll
        @JvmStatic
        fun setup() {
            /*val path = Paths.get("/home/Javatar/IdeaProjects/oldschool-runescape-emulator/data/cache")
            startKoin {
                modules(module {
                    single { DataFile(path.resolve("main_file_cache.dat2")) }

                    single { ReferenceTable(path.resolve("main_file_cache.idx255")) }

                    single { VirtualFileStore(path) }

                    //single { XteaKeyService() }

                    single { DefinitionRepository() }

                    single { Gson() }

                    single { ActorScope() }
                })
            }
            val fileStore = get<VirtualFileStore>()
            fileStore.preload()
            Repository.definitionRepository = definition()*/
        }
    }


    fun addTest() {

        val varbits = VarbitList()
        val bank = Bank(varbits)

        assert(!bank.isFull()) { "Bank should not be full" }

        val whip = StandardItem(4151)
        val whip1 = StandardItem(4151)

        assert(whip == whip1)

        bank.add(whip).test()
        bank.add(whip1)
            .onEach { println(it) }
            .onEachEvent<StackedContainerEvent<Item>> {
                println("Found Whip")
            }
            .test()

        println(bank.tabs[0].elements.contentDeepToString())
        println(bank.elements.contentDeepToString())

        assert(bank.tabs[0].contains(StandardItem(4151))) { "tab 0 does not contain 4151" }

        val items = bank.elements

        assert(items[0].id == 4151)

        assert(items[0].amount == 2) { "Failed to stack whip." }

    }


    fun tabTests() {
        val varbits = VarbitList()
        val bank = Bank(varbits)

        bank.currentTab = 1

        assert(varbits[VarBits.CURRENT_BANK_TAB.varBitId] == 1)

        bank.add(StandardItem(4151)).test()

        assert(bank.tabs[1].contains(StandardItem(4151)))

        assert(bank.tabTwoCount == 1)

    }


    fun noteTest() {
        val varbits = VarbitList()
        val bank = Bank(varbits)

        bank.add(StandardItem(4151)).test()

        assert(bank.contains(StandardItem(4151)))

        bank.add(StandardItem(4152, 10)).test()

        println(bank.elements.contentDeepToString())

        assert(bank.find { it == StandardItem(4151) }?.amount == 11)

    }


    fun removeTest() {
        val varbits = VarbitList()
        val bank = Bank(varbits)

        bank.add(StandardItem(4151)).test()

        assert(bank.contains(StandardItem(4151))) { "Failed to add item to bank. (removeTest)" }

        assert(!bank.isEmpty())

        bank.remove(StandardItem(4151)).test()

        assert(bank.isEmpty()) { "Bank is not empty, should be empty. ${bank.elements.contentDeepToString()}" }

        bank.add(StandardItem(4152, 10)).test()

        assert(bank.find { it == StandardItem(4151) }?.amount == 10)

        bank.remove(StandardItem(4151, 15))
            .onEach { println(it) }
            .test()

        assert(bank.isEmpty())

        bank.add(StandardItem(4152, 10)).test()

        bank.remove(StandardItem(4152, 15))
            .onEach { println(it) }
            .test()

        assert(!bank.isEmpty())


    }


    fun placeholderAdd() {
        val varbits = VarbitList()
        val bank = Bank(varbits)

        bank.placeHoldersEnabled = 1

        assert(bank.currentTab == 0)

        bank.add(StandardItem(4151)).test()

        assert(bank.contains(StandardItem(4151)))

        bank.remove(StandardItem(4151)).test()

        val def = ItemDefinitionFactory.provide(4151)

        println(def.placeholderLink)

        assert(bank.contains(StandardItem(def.placeholderLink)))

    }


    fun attrTest() {


    }

}