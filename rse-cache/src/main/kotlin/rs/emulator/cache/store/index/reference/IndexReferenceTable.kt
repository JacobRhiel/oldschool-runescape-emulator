package rs.emulator.cache.store.index.reference

import io.netty.buffer.Unpooled
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap
import org.koin.core.*
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.store.VirtualFileStore
import rs.emulator.cache.store.data.DataFile
import rs.emulator.cache.store.file.StoreFile
import rs.emulator.cache.store.index.archive.Archive
import rs.emulator.cache.store.index.archive.file.EntryFile
import rs.emulator.cache.store.reference.ReferenceTable
import rs.emulator.cache.store.reference.table.IndependentReferenceTable
import java.nio.file.FileStore

/**
 *
 * @author Chk
 */
class IndexReferenceTable(parent: Int)
    : KoinComponent, IndependentReferenceTable<Archive>(parent)
{

    private val referenceTable = inject<ReferenceTable>()

    var protocol: Int = 6

    var version: Int = 0

    var named: Boolean = true

    var hash: Int = 0

    var archiveCount = 0

    private lateinit var ids: IntArray

    private fun decodeHeader(reader: BufferedReader)
    {

        protocol = reader.getUnsigned(DataType.BYTE).toInt()

        require(!(protocol < 5 || protocol > 7)) { "Unsupported protocol: $protocol" }

        if(protocol >= 6)
            version = reader.getSigned(DataType.INT).toInt()

        hash = reader.getUnsigned(DataType.BYTE).toInt()

        named = 1 and hash != 0

        archiveCount = if(protocol >= 7) reader.bigSmart else reader.getUnsigned(DataType.SHORT).toInt()

        ids = IntArray(archiveCount) { it.inc() - 1 }

        println("archive count: $archiveCount")

    }

    override fun createEntry(identifier: Int): Archive
    {

        var offset = 0

        val reader = referenceTable.value.fetchIndex(parent).decompressedBuffer

        decodeHeader(reader)

        val archive = Archive(parent, identifier)

        val idBuffer = copyBufferToReader(reader, length = archiveCount * Short.SIZE_BYTES).also { offset += it.readableBytes}

        //todo: find the id correctly? typically the entire file is parsed and loaded at once,
        //we are no longer doing this for memory/performance.
        ids.indices.forEach { _ -> (if(protocol >= 7) idBuffer.bigSmart else idBuffer.getUnsigned(DataType.SHORT).toInt()) + if(identifier == 0) 0 else ids[identifier - 1] }

        ids[if(identifier == 0) 0 else identifier] = identifier

        val crcBuffer = copyBufferToReader(reader, length = archiveCount * Int.SIZE_BYTES).also { offset += it.readableBytes}

        crcBuffer.buffer.skipBytes((identifier - 1) * Int.SIZE_BYTES)

        archive.crc = crcBuffer.getSigned(DataType.INT).toInt()

        val versionBuffer = copyBufferToReader(reader, length = archiveCount * Int.SIZE_BYTES).also { offset += it.readableBytes}

        versionBuffer.buffer.skipBytes((identifier - 1) * Int.SIZE_BYTES)

        archive.version = versionBuffer.getSigned(DataType.INT).toInt()

        val archiveCountBuffer = copyBufferToReader(reader, length = archiveCount * Short.SIZE_BYTES).also { offset += it.readableBytes}

        archiveCountBuffer.buffer.skipBytes((identifier - 1) * Short.SIZE_BYTES)

        val count = if(protocol >= 7) archiveCountBuffer.bigSmart else archiveCountBuffer.getUnsigned(DataType.SHORT).toInt()

        archive.entryCount = count

        return archive

    }

}