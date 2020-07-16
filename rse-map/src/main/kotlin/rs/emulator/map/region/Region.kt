package rs.emulator.map.region

import io.reactivex.processors.PublishProcessor
import rs.emulator.map.Positionable

/**
 *
 * @author Chk
 */
class Region(val grid: RegionGrid)
{

    private val playerList = mutableMapOf<Int, Positionable>()

    private val npcList = mutableMapOf<Int, Positionable>()

    //tile hash as key
    private val groundItemList = mutableMapOf<Int, Positionable>()

    //tile hash as key
    private val locList = mutableMapOf<Int, Positionable>()

    private val actorProcessor = PublishProcessor.create<Positionable>()

    private val groundItemProcessor = PublishProcessor.create<Positionable>()

    private val locProcessor = PublishProcessor.create<Positionable>()

    fun addPlayer(index: Int, positionable: Positionable) = playerList.computeIfAbsent(index) { positionable }

    fun addNpc(index: Int, positionable: Positionable) = npcList.computeIfAbsent(index) { positionable }

    fun addGroundItem(tileHash: Int, positionable: Positionable) = groundItemList.computeIfAbsent(tileHash) { positionable }

    fun addLoc(tileHash: Int, positionable: Positionable) = locList.computeIfAbsent(tileHash) { positionable }

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