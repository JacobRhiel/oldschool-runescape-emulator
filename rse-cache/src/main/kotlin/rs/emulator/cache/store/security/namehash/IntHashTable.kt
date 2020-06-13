package rs.emulator.cache.store.security.namehash

/**
 *
 * @author Chk
 */
class IntHashTable(hashes: IntArray)
{

    var array: IntArray

    init
    {

        val var2 = (1 until (hashes.size shr 1) + hashes.size).sumBy { it shl 1 }

        array = IntArray(var2 * 2)

        (array.indices).forEach { array[it] = -1 }

        var var4 = 0

        (hashes.indices).forEach {

            array[var4 + var4 + 1] = it

            var4 = (hashes[it] and var2 - 1)

            while(array[var4 + var4 + 1] != -1)
                var4 = var4 + 1 and var2 - 1

            array[var4 + var4] = hashes[it]

        }

    }

    fun get(var1: Int) : Int
    {

        val var2 = (array.size shr 1) - 1

        var var3 = var1 and var2

        var var4 = array[var3 + var3 + 1]

        while(var4 != -1)
        {
            return if (array[var3 + var3] == var1)
                var4
            else
            {
                var3 = var3 + 1 and var2
                var3
            }
        }

        return -1

    }

}