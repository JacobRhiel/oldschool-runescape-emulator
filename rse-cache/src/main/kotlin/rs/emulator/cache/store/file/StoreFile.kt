package rs.emulator.cache.store.file

import org.koin.core.KoinComponent
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.store.compression.Compressible
import rs.emulator.cache.store.compression.CompressionType
import rs.emulator.cache.store.reference.table.IndependentReferenceTable

/**
 *
 * @author Chk
 */
abstract class StoreFile(val parent: Int,
                         val identifier: Int) : KoinComponent, Compressible()
{

    var sectorLength: Int = 0

    var compressionType: CompressionType = CompressionType.NONE

    var referenceSector: Int = 0

    var referenceIndex: Int = 0

    var referenceLength: Int = 0

    var nameHash: Int = -1

    var loaded: Boolean = false

    abstract val table: IndependentReferenceTable<*>

    abstract fun fetchBuffer(decompressed: Boolean = false) : BufferedReader

}