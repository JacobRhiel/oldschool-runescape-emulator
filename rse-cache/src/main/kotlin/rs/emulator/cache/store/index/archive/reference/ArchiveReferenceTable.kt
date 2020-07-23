package rs.emulator.cache.store.index.archive.reference

import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.store.data.DataFile
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

    override fun loadTable(decompressed: Boolean): IndependentReferenceTable<EntryFile>
    {

        val idx = referenceTable.fetchIndex(idx)

        val archive = idx.readArchive(parent)

        val buffer = archive.fetchBuffer(decompressed)

        if(archive.entryCount == 0)
        {
            println("no entries")
            return this
        }
        else if(archive.entryCount == 1) //todo: does this actually happen? assuming mapscape/landscape
        {
            val entryFile = lookup(0)
            entryFile.referenceIndex = 0
            entryFile.referenceLength = buffer.length
            return this
        }

        buffer.markReaderIndex(buffer.readableBytes - 1)

        val chunks = buffer.getUnsigned(DataType.BYTE).toInt()

        buffer.resetReaderIndex()

        buffer.markReaderIndex(buffer.readableBytes - 1 - chunks * archive.entryCount * 4)

        val chunkSizes = Array(archive.entryCount) { IntArray(chunks) }

        val filesSize = IntArray(archive.entryCount)

        for (chunk in 0 until chunks)
        {
            var chunkSize = 0
            for (id in 0 until archive.entryCount)
            {
                val entryFile = lookup(id)
                val delta: Int = buffer.getSigned(DataType.INT).toInt()
                chunkSize += delta // size of this chunk
                chunkSizes[id][chunk] = chunkSize // store size of chunk
                filesSize[id] += chunkSize // add chunk size to file size
                entryFile.referenceLength = chunkSize
            }
        }

        val fileContents = arrayOfNulls<ByteArray>(archive.entryCount)

        val fileOffsets = IntArray(archive.entryCount)

        for (i in 0 until archive.entryCount)
            fileContents[i] = ByteArray(filesSize[i])

        buffer.resetReaderIndex()

        for (chunk in 0 until chunks)
        {
            for (id in 0 until archive.entryCount)
            {
                val chunkSize = chunkSizes[id][chunk]
                val entryFile = lookup(id)
                entryFile.referenceIndex = buffer.readerIndex()
                buffer.readBytes(fileContents[id]!!, fileOffsets[id], chunkSize)
                entryFile.data = BufferedReader(fileContents[id]!!)
                fileOffsets[id] += chunkSize
            }
        }

        return this

    }

    override fun createEntry(identifier: Int): EntryFile = EntryFile(idx, parent, identifier)

}