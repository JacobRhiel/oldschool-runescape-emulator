package rs.emulator.cache.store.reference

import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap
import rs.emulator.cache.store.access.AccessType
import rs.emulator.cache.store.index.Index
import java.nio.file.Path

/**
 *
 * @author Chk
 */
class ReferenceTable(path: Path,
                     accessType: AccessType = AccessType.READ)
    : Index(255, path, accessType)
{

    private val indexes = Int2ObjectAVLTreeMap<Index>()

    fun getIndexCount(): Int = (raf.length() / 6).toInt()

    fun readIndex(identifier: Int) = readEntry(identifier, fetchIndex(identifier))

    fun fetchIndex(identifier: Int, accessType: AccessType = AccessType.READ): Index
    {
        println("index: $identifier")
        return if(indexes.containsKey(identifier))
            indexes[identifier]!!
        else
        {

            println("index isnt registered")

            val index = Index(identifier, path.parent.resolve("main_file_cache.idx$identifier"))

            indexes[identifier] = index

            readIndex(identifier)

            index

        }
    }

}