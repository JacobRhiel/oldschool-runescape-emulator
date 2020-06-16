package rs.emulator.cache.store.compression

import io.netty.buffer.Unpooled
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream
import org.apache.commons.compress.utils.IOUtils
import org.bouncycastle.crypto.engines.XTEAEngine
import org.bouncycastle.crypto.params.KeyParameter
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.store.Crc32
import rs.emulator.cache.store.file.StoreFile
import rs.emulator.encryption.xtea.XTEA
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.CRC32
import java.util.zip.GZIPInputStream

/**
 *
 * @author Chk
 */
abstract class Compressible : Compressor
{

    private val crc32 = CRC32()

    var hash: Int = 0

    var version: Int = 0

    private val xtea
        get() = XTEAEngine()

    fun decompress(storeFile: StoreFile, reader: BufferedReader): BufferedReader = decompress(storeFile, reader, null)

    fun decompress(storeFile: StoreFile, reader: BufferedReader, keys: IntArray?): BufferedReader
    {

        val opcode = reader.getUnsigned(DataType.BYTE).toInt()

        val compressionType = CompressionType.compressionForOpcode(opcode) ?: throw Error("") //todo return null

        storeFile.apply { this.compressionType = compressionType }

        val compressedLength = reader.getSigned(DataType.INT).toInt()

        if (compressedLength < 0 || compressedLength > 1000000)
            throw RuntimeException("Invalid data: $compressedLength")

        val crc = Crc32()

        crc.update(reader.toArray(), 0, 5) // compression + length

        //crc32.update(reader.toArray(), 0, 5) // compression + length

        var data: ByteArray

        var encryptedData: ByteArray

        var decryptedData: ByteArray

        when(compressionType)
        {

            CompressionType.NONE ->
            {
                encryptedData = fetchEncryptedData(crc, reader, compressedLength)
                decryptedData = decrypt(encryptedData, encryptedData.size, keys)
                data = decryptedData
            }

            else ->
            {

                encryptedData = fetchEncryptedData(crc, reader, compressedLength + 4)

                decryptedData = decrypt(encryptedData, encryptedData.size, keys)

                val reader = BufferedReader(Unpooled.wrappedBuffer(decryptedData))

                val decompressedLength: Int = reader.getUnsigned(DataType.INT).toInt()

                data = decompress(decryptedData, compressedLength, compressionType.algorithm)

                assert(data.size == decompressedLength)

            }

        }

        hash = crc.hash


        return BufferedReader(data)

    }

    private fun fetchEncryptedData(crc32: Crc32, reader: BufferedReader, length: Int): ByteArray
    {

        val encryptedData = ByteArray(length)

        reader.readBytes(encryptedData)

        crc32.update(encryptedData, 0, length)

        if(reader.readableBytes >= 2)
        {

            val version = reader.getUnsigned(DataType.SHORT).toInt()

            this.version = version

            assert(version != -1)

        }

        return encryptedData

    }

    private fun encrypt(keys: IntArray?, data: ByteArray, length: Int): ByteArray = if (keys == null) data else XTEA.encipher(keys, data, length)

    private fun decrypt(data: ByteArray, length: Int, keys: IntArray?): ByteArray = if (keys == null) data else XTEA.decipher(keys, data, 0, length)

}