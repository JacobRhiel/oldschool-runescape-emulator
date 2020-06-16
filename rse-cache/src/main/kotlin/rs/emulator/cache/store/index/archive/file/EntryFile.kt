package rs.emulator.cache.store.index.archive.file

import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.store.data.DataFile
import rs.emulator.cache.store.file.StoreFile
import rs.emulator.cache.store.reference.ReferenceTable
import rs.emulator.cache.store.reference.table.IndependentReferenceTable

/**
 *
 * @author Chk
 */
class EntryFile(private val idx: Int,
                parent: Int,
                identifier: Int
)
    : KoinComponent, StoreFile(parent, identifier)
{

    private val referenceTable: ReferenceTable = get()

    private val dataFile: DataFile = get()

    override val table: IndependentReferenceTable<StoreFile>
        get() = TODO("Not yet implemented")

    override fun fetchBuffer(decompressed: Boolean) : BufferedReader
    {

        val idx = referenceTable.fetchIndex(idx)

        val archive = idx.readArchive(parent)

        var buffer = dataFile.read(idx.identifier, parent, archive.referenceSector, archive.sectorLength)

        if(decompressed)
            buffer = archive.decompress(archive, buffer)

        val out = ByteArray(referenceLength)

        buffer.skipBytes(referenceIndex)

        buffer.readBytes(out, 0, referenceLength)

        return BufferedReader(out)

    }

}