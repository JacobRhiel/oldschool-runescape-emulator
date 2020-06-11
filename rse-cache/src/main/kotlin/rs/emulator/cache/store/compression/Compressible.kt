package rs.emulator.cache.store.compression

import io.netty.buffer.Unpooled
import org.bouncycastle.crypto.engines.XTEAEngine
import org.bouncycastle.crypto.params.KeyParameter
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.store.file.StoreFile
import java.util.zip.CRC32

/**
 *
 * @author Chk
 */
interface Compressible : Compressor
{

    private val crc32
        get() = CRC32()

    private val hash
        get() = crc32.value.toInt()

    private val xtea
        get() = XTEAEngine()

    fun decompress(storeFile: StoreFile, reader: BufferedReader): BufferedReader = decompress(storeFile, reader, null)

    fun decompress(storeFile: StoreFile, reader: BufferedReader, keys: ByteArray?): BufferedReader
    {

        val opcode = reader.getUnsigned(DataType.BYTE).toInt()

        val compressionType = CompressionType.compressionForOpcode(opcode) ?: throw Error("") //todo return null

        storeFile.apply { this.compressionType = compressionType }

        val compressedLength = reader.getSigned(DataType.INT).toInt()

        if (compressedLength < 0 || compressedLength > 1000000)
            throw RuntimeException("Invalid data: $compressedLength")

        crc32.update(reader.toArray(), 0, 5) // compression + length

        var data: ByteArray

        var encryptedData: ByteArray

        var decryptedData: ByteArray

        when(compressionType)
        {

            CompressionType.NONE ->
            {
                encryptedData = fetchEncryptedData(reader, compressedLength)
                decryptedData = decrypt(encryptedData, encryptedData.size, keys)
                data = decryptedData
            }

            else ->
            {

                encryptedData = fetchEncryptedData(reader, compressedLength + 4)

                decryptedData = decrypt(encryptedData, encryptedData.size, keys)

                val reader = BufferedReader(Unpooled.wrappedBuffer(decryptedData))

                val decompressedLength: Int = reader.getUnsigned(DataType.INT).toInt()

                println("dd: " + decryptedData.toTypedArray().contentDeepToString())

                data = decompress(decryptedData, compressedLength, compressionType.algorithm)

                assert(data.size == decompressedLength)

            }

        }

        println("data size: " + data.size)

        return BufferedReader(data)

    }

    private fun fetchEncryptedData(reader: BufferedReader, length: Int): ByteArray
    {

        val encryptedData = ByteArray(length)

        reader.readBytes(encryptedData)

        crc32.update(encryptedData, 0, length)

        if(reader.readableBytes >= 2)
        {

            val version = reader.getUnsigned(DataType.SHORT).toInt()

            assert(version != -1)

        }

        return encryptedData

    }

    private fun encrypt(keys: ByteArray?, data: ByteArray, length: Int): ByteArray
    {

        if(keys == null)
            return data

        xtea.init(true, KeyParameter(keys))

        val outByteArray = byteArrayOf()

        xtea.processBlock(data, 0, outByteArray, 0)

        return outByteArray

    }

    private fun decrypt(data: ByteArray, length: Int, keys: ByteArray?): ByteArray
    {

        if(keys == null)
            return data

        xtea.init(false, KeyParameter(keys))

        val outByteArray = byteArrayOf()

        xtea.processBlock(data, 0, outByteArray, 0)

        return outByteArray

    }

}