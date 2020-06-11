package rs.emulator.cache.store.compression

import org.apache.commons.compress.compressors.CompressorStreamFactory

/**
 *
 * @author Chk
 */
enum class CompressionType(val opcode: Int, val algorithm: String)
{

    NONE(0, ""),

    BZIP(1, CompressorStreamFactory.BZIP2),

    GZIP(2, CompressorStreamFactory.GZIP)

    ;

    companion object
    {

        fun compressionForOpcode(opcode: Int) = values().firstOrNull { it.opcode == opcode }

    }

}