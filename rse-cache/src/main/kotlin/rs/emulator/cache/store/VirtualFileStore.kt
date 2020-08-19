package rs.emulator.cache.store

import com.google.common.primitives.Ints
import io.netty.buffer.Unpooled
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.buffer.writer.BufferedWriter
import rs.emulator.cache.store.compression.CompressionType
import rs.emulator.cache.store.data.DataFile
import rs.emulator.cache.store.index.Index
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.cache.store.reference.ReferenceTable
import rs.emulator.encryption.huffman.HuffmanCodec
import java.io.Closeable
import java.nio.file.Path

/**
 *
 * @author Chk
 */
class VirtualFileStore(private val path: Path) : KoinComponent, Closeable {

    private val dataFile: DataFile = get()

    private val referenceTable: ReferenceTable = get()

    lateinit var huffman: HuffmanCodec

    fun preload() {

        referenceTable.fetchIndexList()

        huffman = HuffmanCodec(
            fetchIndex(IndexConfig.BINARY.identifier).fetchNamedArchive("huffman")!!.fetchEntry(0).fetchBuffer(true)
                .toArray()
        )

    }

    fun fetchIndex(identifier: Int): Index = referenceTable.fetchIndex(identifier)

    fun fetchIndexHeaderBuffer(): BufferedReader {

        val writer = BufferedWriter()

        writer.put(DataType.BYTE, CompressionType.NONE.opcode)

        writer.put(DataType.INT, referenceTable.getIndexCount() * 8)

        referenceTable.fetchIndexList().forEach {
            writer.put(DataType.INT, it.hash)
            writer.put(DataType.INT, it.table.version)
        }

        return BufferedWriter(
            Unpooled.wrappedBuffer(
                writer.byteBuf.array().copyOf(writer.byteBuf.readableBytes())
            )
        ).toBufferedReader()

    }

    fun fetchIndexCrcHashes() =
        referenceTable.fetchIndexList().filter { it.identifier <= 20 }.map { it.hash }.toIntArray()

    fun fetchIndexTableData(identifier: Int): BufferedReader {

        val idxEntry = referenceTable.fetchIndex(identifier)

        return dataFile.read(
            referenceTable.identifier,
            idxEntry.identifier,
            idxEntry.referenceSector,
            idxEntry.sectorLength
        )

    }

    fun fetchArchiveCompressed(index: Int, archive: Int): BufferedReader {

        val idx = fetchIndex(index)

        val group = idx.fetchArchive(archive)

        var data = idx.readArchive(group.identifier)
            .fetchBuffer(false)
            .toArray()

        val compressionType = CompressionType.compressionForOpcode(data[0].toInt())

        val length = Ints.fromBytes(data[1], data[2], data[3], data[4])

        val expectedLength = length + (if (compressionType != CompressionType.NONE) 9 else 5)

        if (expectedLength != length && data.size - expectedLength == 2)
            data = data.copyOf(data.size - 2)

        return BufferedReader(Unpooled.wrappedBuffer(data))

    }

    override fun close() {
        path.fileSystem.close()
    }


}