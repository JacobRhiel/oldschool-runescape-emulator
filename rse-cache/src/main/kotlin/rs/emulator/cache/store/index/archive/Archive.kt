package rs.emulator.cache.store.index.archive

import org.koin.core.get
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.store.data.DataFile
import rs.emulator.cache.store.file.StoreFile
import rs.emulator.cache.store.index.archive.file.EntryFile
import rs.emulator.cache.store.index.archive.reference.ArchiveReferenceTable
import rs.emulator.cache.store.index.reference.IndexReferenceTable
import rs.emulator.cache.store.reference.ReferenceTable
import rs.emulator.cache.store.reference.table.IndependentReferenceTable

/**
 *
 * @author Chk
 */
class Archive(
        parent: Int,
        identifier: Int,
        var named: Boolean = false
) : StoreFile(parent, identifier)
{

    var crc: Int = 0

    var entryCount: Int = 0

    private val referenceTable: ReferenceTable = get()

    private val dataFile: DataFile = get()

    override val table: ArchiveReferenceTable = ArchiveReferenceTable(parent, identifier)

    fun fetchEntry(identifier: Int) = table.lookup(identifier)

    override fun fetchBuffer(decompressed: Boolean, xtea: IntArray?): BufferedReader
    {

        val idx = referenceTable.fetchIndex(parent)

        idx.readArchive(identifier)

        val buffer = dataFile.read(idx.identifier, identifier, referenceSector, sectorLength)

        return if(decompressed)
            decompress(this, buffer, xtea)
        else
            buffer

    }

}