package rs.emulator.cache.store.compression

import org.apache.commons.compress.compressors.CompressorStreamFactory
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

/**
 *
 * @author Chk
 */
interface Compressor
{

    fun decompress(bytes: ByteArray, length: Int, algorithm: String): ByteArray
    {

        val data = applyHeader(algorithm, bytes, length)

        val input = ByteArrayInputStream(data)

        val inputStream = CompressorStreamFactory.getSingleton().createCompressorInputStream(algorithm, input)

        val outputStream = ByteArrayOutputStream()

        var decompressedData: ByteArray = byteArrayOf()

        input.use {

            (outputStream with algorithm).use {

                decompressedData = it read inputStream

            }

        }

        return decompressedData

    }

    private fun applyHeader(algorithm: String, bytes: ByteArray, length: Int): ByteArray
    {

        var data: ByteArray = byteArrayOf()

        when(algorithm)
        {

            CompressorStreamFactory.GZIP ->
            {

                data = bytes.copyOfRange(4, bytes.size)

            }

            CompressorStreamFactory.BZIP2 ->
            {

                /* prepare a new byte array with the bzip2 header at the start */
                val bzip2 = ByteArray(length + 4)

                bzip2[0] = 'B'.toByte()

                bzip2[1] = 'Z'.toByte()

                bzip2[2] = 'h'.toByte()

                bzip2[3] = '1'.toByte()

                System.arraycopy(bytes, 4, bzip2, 4, length)

                data = bzip2

            }

        }

        return data

    }

}