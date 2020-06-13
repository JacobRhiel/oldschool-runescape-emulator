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

    override fun loadTable(): IndependentReferenceTable<EntryFile>
    {

        val idx = referenceTable.fetchIndex(idx)

        println("testing11231231")

        val archive = idx.readArchive(parent)

        println("testing")

        archive.decompressedBuffer = dataFile.read(idx.identifier, parent, archive.referenceSector, archive.sectorLength)

        val buffer = archive.decompress(archive, archive.decompressedBuffer.copy())

        if(archive.entryCount == 0) return this
        else if(archive.entryCount == 1) //todo: does this actually happen? assuming mapscape/landscape
        {
            lookup(0).decompressedBuffer = BufferedReader(buffer.toArray())
            return this
        }

        println("uh: " + buffer.readableBytes)

        buffer.markReaderIndex(buffer.readableBytes - 1)

        val chunks = buffer.getUnsigned(DataType.BYTE).toInt()

        println("chunks: $chunks")

        buffer.resetReaderIndex()

        buffer.markReaderIndex(buffer.readableBytes - 1 - chunks * archive.entryCount * 4)

        val chunkSizes = Array(archive.entryCount) { IntArray(chunks) }

        val filesSize = IntArray(archive.entryCount)

        println(archive.entryCount)

        for (chunk in 0 until chunks)
        {
            var chunkSize = 0
            for (id in 0 until archive.entryCount)
            {
                val delta: Int = buffer.getSigned(DataType.INT).toInt()
                chunkSize += delta // size of this chunk
                chunkSizes[id][chunk] = chunkSize // store size of chunk
                filesSize[id] += chunkSize // add chunk size to file size
                //if(id <= 4151)
                //    println("delta: $delta")
                if(id == 4151)
                {
                    println("delta: $delta")
                    println("chunk size: $chunkSize")
                    println("file size: " + filesSize[id])
                }
            }
        }
        val fileContents = arrayOfNulls<ByteArray>(archive.entryCount)
        val fileOffsets = IntArray(archive.entryCount)

        for (i in 0 until archive.entryCount)
            fileContents[i] = ByteArray(filesSize[i])

        println("reader index: " + buffer.readerIndex())

        buffer.resetReaderIndex()

        for (chunk in 0 until chunks)
        {
            for (id in 0 until archive.entryCount)
            {
                val chunkSize = chunkSizes[id][chunk]
                if(id == 4151)
                    println("reader index: " + buffer.readerIndex())
                buffer.readBytes(fileContents[id]!!, fileOffsets[id], chunkSize)
                fileOffsets[id] += chunkSize
            }
        }

        for(i in 0 until archive.entryCount)
            lookup(i).decompressedBuffer = BufferedReader(fileContents[i]!!)

        return this

    }

    override fun createEntry(identifier: Int): EntryFile = EntryFile(parent, identifier)

}