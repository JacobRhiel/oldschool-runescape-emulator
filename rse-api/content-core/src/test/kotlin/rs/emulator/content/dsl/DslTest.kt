package rs.emulator.content.dsl

import org.junit.jupiter.api.Test
import rs.emulator.content.dsl.impl.ObjectContext

/**
 *
 * @author javatar
 */

class DslTest {

    @Test
    fun dsl() {

        ObjectContext(100) {
            perform after 5 seconds {}
            perform after 15 seconds replaceObject(5)

        }

    }

    @Test
    fun damageIdea() {

    }

}
