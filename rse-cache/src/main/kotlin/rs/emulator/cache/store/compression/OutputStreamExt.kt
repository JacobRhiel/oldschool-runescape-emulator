package rs.emulator.cache.store.compression

import org.apache.commons.compress.compressors.*
import java.io.*

/**
 *
 * @author Chk
 */
infix fun ByteArrayOutputStream.with(algorithm: String): CompressorOutputStream
{

    use { output ->

        return CompressorStreamFactory.getSingleton().createCompressorOutputStream(algorithm, output)

    }


}

infix fun CompressorOutputStream.read(inputStream: CompressorInputStream): ByteArray
{

    val buffer = inputStream.readAllBytes()

    val output = ByteArray(buffer.size)

    var n: Int

    inputStream.use { inputStream ->
        while (-1 != inputStream.read(output, 0, buffer.size).also { n = it })
            write(output, 0, n)

        return buffer
    }

}