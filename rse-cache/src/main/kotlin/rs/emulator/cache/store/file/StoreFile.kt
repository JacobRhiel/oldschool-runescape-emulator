package rs.emulator.cache.store.file

import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.store.compression.Compressible
import rs.emulator.cache.store.compression.CompressionType
import rs.emulator.cache.store.reference.table.IndependentReferenceTable

/**
 *
 * @author Chk
 */
abstract class StoreFile(val parent: Int,
                         val identifier: Int) : Compressible
{

    var sectorLength: Int = 0

    var compressionType: CompressionType = CompressionType.NONE

    var referenceSector: Int = 0

    lateinit var decompressedBuffer: BufferedReader

    var loaded: Boolean = false

    abstract val table: IndependentReferenceTable<*>

    open fun read()
    {

    }

    open fun write()
    {

    }



}