package rs.emulator.cache.store

import java.util.zip.CRC32

class Crc32
{
    private val crc32 = CRC32()
    fun update(data: ByteArray?, offset: Int, length: Int)
    {
        crc32.update(data, offset, length)
    }

    val hash: Int
        get() = crc32.value.toInt()
}