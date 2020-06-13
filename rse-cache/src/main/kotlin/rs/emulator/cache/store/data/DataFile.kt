package rs.emulator.cache.store.data

import io.netty.buffer.Unpooled
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.store.access.AccessType
import rs.emulator.cache.store.compression.Compressible
import rs.emulator.utilities.logger.error
import java.io.Closeable
import java.io.RandomAccessFile
import java.nio.file.Path

/**
 *
 * @author Chk
 */
class DataFile(
    path: Path,
    accessType: AccessType = AccessType.READ
) : Compressible, Closeable
{

    private val dat = RandomAccessFile(path.toFile(), accessType.rafAccess)

    private val sectorSize = 520

    fun read(index: Int, archive: Int, sectorId: Int, size: Int): BufferedReader
    {

        println("index: " + index + ", " + archive + ", " + sectorId)

        if (sectorId <= 0L || dat.length() / sectorSize < sectorId.toLong())
            error("bad read, index{}, dat length {}, requested sector {}", index, dat.length(), sectorId)

        val bufferedReader = BufferedReader(Unpooled.wrappedBuffer(ByteArray(sectorSize)))

        val bufferedWriter = Unpooled.buffer(size)

        var part = 0

        var readBytesCount = 0

        var nextSector: Int

        var sector = sectorId

        var headerSize: Byte

        var currentIndex: Int

        var currentPart: Int

        var currentArchive: Int

        while (size > readBytesCount)
        {

            if (sector == 0)
                error("Unexpected EOF")

            dat.seek(sectorSize.times(sector).toLong())

            val archiveExceedsShort = archive > 0xFFFF

            headerSize = if(archiveExceedsShort) 10 else 8

            var dataBlockSize = size - readBytesCount

            if (dataBlockSize > sectorSize - headerSize)
                dataBlockSize = sectorSize - headerSize

            val bufferByteArray = bufferedReader.toArray()

            checkReadSize(index, archive, bufferByteArray, 0, headerSize + dataBlockSize)

            val bytes = bufferByteArray.copyOfRange(0, headerSize + dataBlockSize)

            bufferedReader.markReaderIndex()

            currentArchive = bufferedReader.getUnsigned(if(archiveExceedsShort) DataType.INT else DataType.SHORT).toInt()

            currentPart = bufferedReader.getUnsigned(DataType.SHORT).toInt()

            nextSector = bufferedReader.getUnsigned(DataType.TRI_BYTE).toInt()

            currentIndex = bufferedReader.getUnsigned(DataType.BYTE).toInt()

            bufferedReader.resetReaderIndex()

            if (archive != currentArchive || currentPart != part || index != currentIndex)
                error(
                    "Invalid sector read: index {} != current index {}, archive {} != current archive {}, part {} != current part {}",
                    index, currentIndex, archive, currentArchive, part, currentPart
                )

            if (nextSector < 0 || dat.length() / sectorSize < nextSector.toLong())
                error("Invalid next sector")

            sector = nextSector

            bufferedWriter.writeBytes(bytes, headerSize.toInt(), dataBlockSize)

            readBytesCount += dataBlockSize

            ++part

        }

        return BufferedReader(bufferedWriter.array().copyOfRange(0, bufferedWriter.readableBytes()))

    }

    private fun checkReadSize(index: Int, archive: Int, bytes: ByteArray, offset: Int, length: Int)
    {

        val bytesRead = dat.read(bytes, offset, length)

        if (bytesRead != length)
            error("Short read when reading file data for {}/{}", index, archive)

    }

    override fun close() = dat.close()

}