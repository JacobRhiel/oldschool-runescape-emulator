package rs.emulator.gson

import com.google.gson.GsonBuilder
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import rs.emulator.entity.material.items.Item
import rs.emulator.entity.material.items.StandardItem
import rs.emulator.entity.material.items.Wearable
import rs.emulator.entity.player.storage.containers.Inventory

/**
 *
 * @author javatar
 */

class ItemAdapterTest {

    @Test
    fun testAdapter() {


        val gson =
            GsonBuilder().setPrettyPrinting().disableHtmlEscaping().registerTypeAdapter(Item::class.java, ItemAdapter())
                .create()

        startKoin {
            modules(module {
                single { gson }
            })
        }

        val inventory = Inventory()

        inventory.add(StandardItem(12, 1))
        inventory.add(Wearable(id = 4151, amount = 1))

        val s = gson.toJson(inventory)

        //println(s)

        val con = gson.fromJson(s, Inventory::class.java)

        con.array.forEach {
            println(it)
        }

    }

}