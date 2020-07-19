package rs.emulator.entity.widgets.events

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.widgets.Component
import rs.emulator.entity.widgets.WidgetEvent

/**
 *
 * @author javatar
 */

class ComponentClickEvent(override val source: Component, player: IPlayer, val option: Int) : WidgetEvent