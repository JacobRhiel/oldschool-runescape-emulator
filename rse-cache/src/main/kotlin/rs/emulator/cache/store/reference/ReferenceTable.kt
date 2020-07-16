package rs.emulator.cache.store.reference

import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.store.access.AccessType
import rs.emulator.cache.store.data.DataFile
import rs.emulator.cache.store.index.Index
import java.nio.file.Path

/**
 *
 * @author Chk
 */
class ReferenceTable(path: Path,
                     accessType: AccessType = AccessType.READ)
    : KoinComponent, Index(255, path, accessType)
{

    private val indexes = Int2ObjectAVLTreeMap<Index>()

    private val dataFile: DataFile = get()

    fun getIndexCount(): Int = (raf.length() / 6).toInt()

    fun readIndex(identifier: Int) = readEntry(identifier, fetchIndex(identifier))

    fun fetchIndexList(): List<Index> = (0 until getIndexCount()).map { idx -> fetchIndex(idx) }

    fun fetchIndexBuffer(identifier: Int): BufferedReader
    {

        val idx = fetchIndex(identifier)

        return dataFile.read(this.identifier, idx.identifier, idx.referenceSector, idx.sectorLength)

    }

    fun fetchIndex(identifier: Int, accessType: AccessType = AccessType.READ): Index
    {
        return if(indexes.containsKey(identifier))
            indexes[identifier]!!
        else
        {

            val idx = Index(identifier, path.parent.resolve("main_file_cache.idx$identifier"))

            indexes[identifier] = idx

            readIndex(identifier)

            idx.apply { table.load() }

            idx

        }
    }

    override fun fetchBuffer(decompressed: Boolean, xtea: IntArray?): BufferedReader = BufferedReader(0)

}