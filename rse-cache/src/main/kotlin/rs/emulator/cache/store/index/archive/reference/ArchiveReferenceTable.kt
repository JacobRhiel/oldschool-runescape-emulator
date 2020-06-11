package rs.emulator.cache.store.index.archive.reference

import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.cache.store.data.DataFile
import rs.emulator.cache.store.index.archive.Archive
import rs.emulator.cache.store.index.archive.file.EntryFile
import rs.emulator.cache.store.reference.ReferenceTable
import rs.emulator.cache.store.reference.table.IndependentReferenceTable

/**
 *
 * @author Chk
 */
class ArchiveReferenceTable(private val idx: Int,
                            parent: Int)
    : KoinComponent, IndependentReferenceTable<EntryFile>(parent)
{

    private val referenceTable: ReferenceTable = get()

    private val dataFile: DataFile = get()

    override fun createEntry(identifier: Int): EntryFile
    {

        val idx = referenceTable.fetchIndex(idx)

        val archive = idx.fetchArchive(parent)

        referenceTable.fetchIndex(parent).readArchive(parent)

        archive.decompressedBuffer = dataFile.read(idx.identifier, parent, archive.referenceSector, archive.sectorLength)

        println("test: " + archive.decompressedBuffer.toArray().toTypedArray().contentDeepToString())

        val entryIdBuffer = copyBufferToReader(archive.decompressedBuffer, length = archive.entryCount * Short.SIZE_BYTES)

        val previousEntryId = (0 until identifier).sumBy { entryIdBuffer.getUnsigned(DataType.SHORT).toInt() }

        val entryIdentifier = previousEntryId + entryIdBuffer.getUnsigned(DataType.SHORT).toInt()

        //todo name hash

        println("test")

        return EntryFile(parent, entryIdentifier)

    }

}