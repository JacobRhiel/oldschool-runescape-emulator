package rs.emulator.entity.material.containers.impl.bank

import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import rs.emulator.collections.varbits.VarbitList
import rs.emulator.definitions.impl.factories.ItemDefinitionFactory
import rs.emulator.entity.material.containers.ItemContainer
import rs.emulator.entity.material.containers.events.ItemContainerEvent
import rs.emulator.entity.material.containers.events.impl.EmptyContainerEvent
import rs.emulator.entity.material.containers.events.impl.FullContainerEvent
import rs.emulator.entity.material.containers.events.impl.NoSuchItemContainerEvent
import rs.emulator.entity.material.items.Item
import rs.emulator.definitions.data.VarBits
import rs.emulator.definitions.data.id
import rs.emulator.utilities.koin.get


/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
class Bank(varbits: VarbitList, val bankSize: Int = 800, val tabs: Array<BankTab> = Array(9){ BankTab(it) }) : ItemContainer<Item>(95, arrayOf()) {

    override val elements: Array<Item>
        get() = tabs.map { it.toList() }.flatten().toTypedArray()

    var currentTab by varbits.id(VarBits.CURRENT_BANK_TAB)
    var bankWithdrawQuantity by varbits.id(VarBits.BANK_WITHDRAW_QUANTITY)
    var bankWithdrawNote by varbits.id(VarBits.BANK_WITHDRAW_NOTE)
    var bankInsertItem by varbits.id(VarBits.BANK_INSERT_ITEM)
    var tabOneCount by varbits.id(VarBits.BANK_TAB_ONE_COUNT)
    var tabTwoCount by varbits.id(VarBits.BANK_TAB_TWO_COUNT)
    var tabThreeCount by varbits.id(VarBits.BANK_TAB_THREE_COUNT)
    var tabFourCount by varbits.id(VarBits.BANK_TAB_FOUR_COUNT)
    var tabFiveCount by varbits.id(VarBits.BANK_TAB_FIVE_COUNT)
    var tabSixCount by varbits.id(VarBits.BANK_TAB_SIX_COUNT)
    var tabSevenCount by varbits.id(VarBits.BANK_TAB_SEVEN_COUNT)
    var tabEightCount by varbits.id(VarBits.BANK_TAB_EIGHT_COUNT)
    var tabNineCount by varbits.id(VarBits.BANK_TAB_NINE_COUNT)

    var placeHoldersEnabled by varbits.id(VarBits.PLACE_HOLDERS_ENABLED)

    val tabCounts: IntArray
        get() = intArrayOf(
            tabOneCount,
            tabTwoCount,
            tabThreeCount,
            tabFourCount,
            tabFiveCount,
            tabSixCount,
            tabSevenCount,
            tabEightCount,
            tabNineCount
        )

    fun getTabCount(tab: Int) = tabCounts[tab]

    fun getTabBaseIndex(tab: Int) = if (tab == 0) tabCounts.sum() - getTabCount(0) else (1 until tab).sumBy { getTabCount(it) }

    fun getTabByIndex(index: Int): Int {
        if(index == -1)
            return currentTab
        for (tab in tabs.indices) {
            val baseIndex = getTabBaseIndex(tab)
            val count = getTabCount(tab)
            if (index in baseIndex..(baseIndex + count)) {
                return tab
            }
        }
        return currentTab
    }

    override fun isFull(): Boolean {
        return tabCounts.sum() >= bankSize
    }

    override fun isEmpty(): Boolean {
        return tabCounts.sum() == 0
    }

    override fun add(element: Item): Flow<ItemContainerEvent<Item>> = flow {
        if (isFull()) {
            emit(FullContainerEvent(element))
            return@flow
        }
        val elements = this@Bank.elements

        val def = ItemDefinitionFactory.provide(element.id)

        val index = elements.indexOfFirst { it == element || it.id == def.placeholderLink }

        val tab = tabs[getTabByIndex(index)]
        if (index != -1 && elements[index].id == def.placeholderLink) {
            emitAll(tab.replace(element.toPlaceholder(), element))
        } else {
            emitAll(tab.add(element.toUnnoted()))
        }

        setTabCount(tab.tabId, tab.size)
    }

    override fun remove(element: Item): Flow<ItemContainerEvent<Item>> = flow {
        if (isEmpty())
            return@flow emit(EmptyContainerEvent(element))
        val index = elements.indexOf(element)
        if (index != -1) {
            val tab = tabs[getTabByIndex(index)]

            if (bankWithdrawNote == 1) {
                emitAll(tab.remove(element.toNoted()))
            } else if (placeHoldersEnabled == 1) {
                emitAll(tab.replace(element, element.toPlaceholder()))
            } else {
                emitAll(tab.remove(element))
            }

            setTabCount(tab.tabId, tab.size)
        } else emit(NoSuchItemContainerEvent(element))
    }

    private fun setTabCount(tab: Int, count: Int) {
        when (tab) {
            0 -> tabOneCount = count
            1 -> tabTwoCount = count
            2 -> tabThreeCount = count
            3 -> tabFourCount = count
            4 -> tabFiveCount = count
            5 -> tabSixCount = count
            6 -> tabSevenCount = count
            7 -> tabEightCount = count
            8 -> tabNineCount = count
        }
    }

    override fun toString(): String {
        return get<Gson>().toJson(tabs)
    }
}