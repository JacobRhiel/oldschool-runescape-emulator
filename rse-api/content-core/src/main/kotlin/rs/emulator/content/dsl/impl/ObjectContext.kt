package rs.emulator.content.dsl.impl

import rs.emulator.content.dsl.AbstractContext
import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

class ObjectContext(val objectId : Int, logic : suspend ObjectContext.(IPlayer) -> Unit) : AbstractContext<ObjectContext>() {

    val perform : ObjectContext get() = this

    infix fun ObjectContext.replaceObject(objectId: Int) : suspend ObjectContext.() -> Unit = {
        println(objectId)
    }
}