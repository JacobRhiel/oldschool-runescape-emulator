package test

import io.reactivex.Observable
import org.junit.jupiter.api.Test
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.messages.IWidgetMessages
import rs.emulator.region.zones.RegionZone
import rs.emulator.region.zones.ZoneEvent
import rs.emulator.region.zones.events.EnterZoneEvent
import java.util.concurrent.TimeUnit

/**
 *
 * @author javatar
 */

class RegionZoneTest {

    @Test
    fun regionZoneTest() {
        val pvpzone = RegionZone(
            0,
            0,
            0,
            10,
            10
        )
        val player: IPlayer? = null
        if (player != null) {
            data class Poison(override val entity: IPlayer, val dmg: Int) :
                ZoneEvent<IPlayer>
            pvpzone.reactiveZone.subscribe<Poison> {
                it.entity.messages().ofType<IWidgetMessages>()
                    .sendChatMessage("The poison damages you by ${it.dmg}.")
            }
            pvpzone.reactiveZone.subscribe<EnterZoneEvent> {
                if (it.entity is IPlayer) {
                    pvpzone.reactiveZone.add(Observable.interval(2, TimeUnit.SECONDS).subscribe {
                        pvpzone.reactiveZone.onNext(Poison(player, 3))
                    })
                }
            }
        }
        Observable.timer(5, TimeUnit.MINUTES).subscribe {
            pvpzone.reactiveZone.dispose()
        }
    }

}