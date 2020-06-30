package rs.emulator.entity.actor.player

import rs.emulator.entity.actor.IActor
import rs.emulator.entity.actor.player.messages.IContainerMessages
import rs.emulator.entity.actor.player.messages.IVarpMessage
import rs.emulator.entity.actor.player.messages.IWidgetMessages
import rs.emulator.entity.actor.player.storage.IItemContainerManager
import rs.emulator.entity.actor.player.storage.container.ItemContainer
import rs.emulator.entity.material.items.Item

interface IPlayer : IActor, IVarpMessage, IWidgetMessages, IContainerMessages {

    fun containerManager() : IItemContainerManager

    fun varpMessages() : IVarpMessage = this
    fun widgetMessages() : IWidgetMessages = this
    fun containerMessages() : IContainerMessages = this
}