import com.xenomachina.argparser.ArgParser
import org.junit.jupiter.api.Test
import rs.emulator.plugin.commands.impl.SpawnItem

class Test {

    @Test
    fun test() {

        val cmd = "item 4151 5"
        val name = cmd.split(" ")[0]

        val substringAfter = cmd.substringAfter(name).trim()

        println(substringAfter)

        val parser = ArgParser(substringAfter.split(" ").toTypedArray())

        parser.parseInto(::SpawnItem).run {
            println("Item $id amount $amt")
        }

    }

}