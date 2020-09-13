import org.junit.jupiter.api.Test
import rs.emulator.entity.material.items.Wearable

class ItemTest {


    @Test
    fun attr() {

        val whip = Wearable(id = 4151)

        whip.attributes["test"] = 1

        val whip1 = Wearable(id = 4151)

        assert(whip != whip1)

        whip1.attributes["test"] = 1

        assert(whip == whip1)

        whip.attributes["test"] = 2

        assert(whip != whip1)

        val whip2 = whip.copy()

        assert(whip == whip2)

    }

    @Test
    fun objAttrTest() {

        val whip = Wearable(id = 4151)

        whip.attributes["test"] = 1 to 2

        val whip1 = whip.copy()

        assert(whip == whip1)

        val p: Pair<Int, Int> = whip1.attributes["test"]

        assert(p.first == 1 && p.second == 2)

    }

}