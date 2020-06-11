package rs.emulator.cache.store.index

import io.netty.buffer.Unpooled
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.store.access.AccessType
import rs.emulator.cache.store.file.StoreFile
import rs.emulator.cache.store.index.archive.Archive
import rs.emulator.cache.store.index.reference.IndexReferenceTable
import rs.emulator.cache.store.reference.table.IndependentReferenceTable
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

    internal val raf = RandomAccessFile(path.toFile(), accessType.rafAccess)

    override val table: IndependentReferenceTable<Archive> = IndexReferenceTable(identifier)

    fun fetchArchive(identifier: Int) = table.lookup(identifier)

    fun readArchive(identifier: Int) = readEntry(identifier, fetchArchive(identifier))

    protected fun readEntry(identifier: Int, storeFile: StoreFile)
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

    }

}