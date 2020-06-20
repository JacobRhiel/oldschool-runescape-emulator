package rs.emulator.cache.store.index

import io.netty.buffer.Unpooled
import org.koin.core.get
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.store.access.AccessType
import rs.emulator.cache.store.data.DataFile
import rs.emulator.cache.store.file.StoreFile
import rs.emulator.cache.store.index.archive.Archive
import rs.emulator.cache.store.index.reference.IndexReferenceTable
import rs.emulator.utilities.logger.error
import java.io.RandomAccessFile
import java.nio.file.Path

/**
 *
 * @author Chk
 */
open class Index(
    identifier: Int,
    internal val path: Path,
    accessType: AccessType = AccessType.READ
) : StoreFile(identifier, identifier)
{

    private val dataFile: DataFile = get()

    internal val raf = RandomAccessFile(path.toFile(), accessType.rafAccess)

    override val table: IndexReferenceTable = IndexReferenceTable(identifier)

    val crc = hash

    fun fetchArchive(identifier: Int): Archive = table.lookup(identifier)

    fun fetchNamedArchive(name: String): Archive?
    {

        if(table.groupCount == 0)
            table.loadReference()

        var archive: Archive? = null

        (0 until table.groupCount).forEach {

            val createdArchive = fetchArchive(it)

            return if(createdArchive.nameHash == hash(name))
            {
                archive = createdArchive
                archive
            }
            else return@forEach

        }

        return archive

    }

    private fun hash(str: String): Int
    {
        var hash = 0
        for (element in str)
            hash = element.toInt() + ((hash shl 5) - hash)
        return hash
    }

    fun readArchive(identifier: Int) = readEntry(identifier, table.lookup(identifier)) as Archive

    protected fun readEntry(identifier: Int, storeFile: StoreFile): StoreFile
    {

        val headerSize = 6

        val reader = BufferedReader(Unpooled.wrappedBuffer(ByteArray(6)))

        raf.seek((identifier * headerSize).toLong())

        val expectedSize = raf.read(reader.toArray())

        if(expectedSize != headerSize)
            error("short read for id {} on index {}. [length: {}]", identifier, identifier, expectedSize)

        val length = reader.getSigned(DataType.TRI_BYTE).toInt()

        val sector = reader.getSigned(DataType.TRI_BYTE).toInt()

        if(length <= 0)
            error("Invalid length[{}] of idx {}, reported: {}.", length, identifier)

        if(sector <= 0)
            error("Invalid sector[{}] for idx {}.", sector, identifier)

        storeFile.sectorLength = length

        storeFile.referenceSector = sector

        return storeFile

    }

    override fun fetchBuffer(decompressed: Boolean): BufferedReader = dataFile.read(255, identifier, referenceSector, sectorLength)

}