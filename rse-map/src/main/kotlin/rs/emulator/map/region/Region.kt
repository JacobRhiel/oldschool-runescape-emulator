package rs.emulator.map.region

import rs.emulator.entity.`object`.IObject
import rs.emulator.entity.actor.npc.INpc
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.material.items.Item
import rs.emulator.region.zones.RegionZone

/**
 *
 * @author Chk
 */
class Region(val grid: RegionGrid)
{

    private val playerList = mutableMapOf<Int, IPlayer>()

    private val npcList = mutableMapOf<Int, INpc>()

    //tile hash as key
    private val groundItemList = mutableMapOf<Int, Item>()

    //tile hash as key
    private val locList = mutableMapOf<Int, IObject>()

    val zones = mutableListOf<RegionZone>()

    fun addPlayer(index: Int, player: IPlayer) = playerList.computeIfAbsent(index) { player }

    fun addNpc(index: Int, npc: INpc) = npcList.computeIfAbsent(index) { npc }

    fun addGroundItem(tileHash: Int, item: Item) = groundItemList.computeIfAbsent(tileHash) { item }

    fun addLoc(tileHash: Int, obj: IObject) = locList.computeIfAbsent(tileHash) { obj }

    fun removePlayer(index: Int) = playerList.remove(index)

    fun removeNpc(index: Int) = npcList.remove(index)

    fun removeGroundItem(tileHash: Int) = groundItemList.remove(tileHash)

    fun removeLoc(tileHash: Int) = groundItemList.remove(tileHash)

    fun updateGroundItem(oldHash: Int, newHash: Int)
    {

        val groundItem = groundItemList.remove(oldHash)

        if(groundItem != null)
            addGroundItem(newHash, groundItem)

    }

    fun updateLoc(oldHash: Int, newHash: Int)
    {

        val loc = locList.remove(oldHash)

        if(loc != null)
            addLoc(newHash, loc)

    }

}