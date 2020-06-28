package rs.emulator.collections

import org.junit.jupiter.api.Test
import rs.emulator.collections.varbits.VarbitList

/**
 *
 * @author javatar
 */

class VarbitDelegateTest {

    @Test
    fun delegate() {

        val varbits = VarbitList()

        var attack : Int by varbits.id(1)

        attack = 4

        var strength : Int by varbits(5)

        strength = 1

        println(strength)

    }

}
