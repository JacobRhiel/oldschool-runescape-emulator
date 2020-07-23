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

    var data: BufferedReader? = null

    override val table: IndependentReferenceTable<StoreFile>
        get() = TODO("Not yet implemented")

    override fun fetchBuffer(decompressed: Boolean, xtea: IntArray?): BufferedReader {

        if (data != null)
            return data!!

        val idx = referenceTable.fetchIndex(idx)

        val archive = idx.readArchive(parent)

        var buffer = dataFile.read(idx.identifier, parent, archive.referenceSector, archive.sectorLength)

        if(decompressed)
            buffer = archive.decompress(archive, buffer, xtea)

        val out = ByteArray(if(archive.entryCount == 1) buffer.readableBytes else referenceLength)

        buffer.skipBytes(referenceIndex)

        if(archive.entryCount > 1)
            buffer.readBytes(out, 0, referenceLength)
        else
            buffer.readBytes(out, 0, buffer.readableBytes)

        val reader = BufferedReader(out)

        data = reader

        return reader

    }

}