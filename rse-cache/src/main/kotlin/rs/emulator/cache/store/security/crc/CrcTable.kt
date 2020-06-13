package rs.emulator.cache.store.security.crc

/**
 *
 * @author Chk
 */
object CrcTable
{

    private var crc16Table: IntArray = IntArray(256)

    private var crc32Table: LongArray = LongArray(256)

    init
    {

        (0 until 256).forEach { index ->

            var crc: Number

            (0 until 8).forEach { _ ->

                var longIndex = index.toLong()

                crc = if (index and 1 == 1)
                {
                    index ushr 1 xor -306674912
                }
                else
                {
                    index ushr 1
                }

                crc16Table[index] = crc.toInt()

                crc = if (longIndex and 1L == 1L)
                {
                    longIndex ushr 1 xor -3932672073523589310L
                }
                else
                {
                    longIndex ushr 1
                }

                crc32Table[index] = crc.toLong()

            }

        }

    }

    fun fetchIdxHash(bytes: ByteArray, length: Int): Int
    {

        var var3 = -1

        (0 until length).forEach {
            var3 = var3 ushr 8 xor crc16Table[(var3 xor bytes[it].toInt()) and 255]
        }

        var3 = var3.inv()

        return var3

    }

}