package rs.emulator.collections

import kotlin.random.Random

/**
 *
 * @author Chk
 */
class RandomizedIndexMap(capacity: Int) : HashMap<Int, Int>(capacity)
{

    private val shuffleRandom = Random

    fun getOrDefault(uid: Int) = getOrDefault(uid, add(uid))

    fun add(uid: Int): Int
    {

        // start at 1 since index 0 is skipped via client.
        val index = computeIfAbsent(uid) { _ ->

            var randomIndex: Int

            do randomIndex = shuffleRandom.nextInt(1, size)
            while(containsValue(randomIndex))

            randomIndex

        }

        if(containsKey(uid))
            return getValue(uid)

        return index

    }

    fun shuffleIndexes(): RandomizedIndexMap
    {

        val keyValues = values.toMutableList().shuffled(shuffleRandom)

        val iterator = keyValues.iterator()

        keys.forEach { key ->

            compute(key) { _, _ -> iterator.next() }

        }

        return this

    }

}