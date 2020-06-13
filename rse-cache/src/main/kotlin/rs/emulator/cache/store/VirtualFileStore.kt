package rs.emulator.cache.store

import org.koin.core.*
import rs.emulator.cache.store.data.DataFile
import rs.emulator.cache.store.index.Index
import rs.emulator.cache.store.reference.ReferenceTable
import rs.emulator.utilities.logger.error
import rs.emulator.utilities.logger.info
import java.io.Closeable
import java.nio.file.Path
import kotlin.collections.get

/**
 *
 * @author Chk
 */
class VirtualFileStore(private val path: Path) : KoinComponent, Closeable
{

    private val dataFile = DataFile(path.resolve("main_file_cache.dat2"))

    val referenceTable: ReferenceTable = get()

    fun fetchIndex(identifier: Int) : Index
    {

        val file = path.resolve("main_file_cache.idx$identifier").toFile()

        if(!file.exists())
            error("No IndexFile exists for identifier: {}", identifier)

        if(referenceTable.fetchIndex(identifier).loaded)
            return referenceTable.fetchIndex(identifier).also { it.loaded = true }

        if(referenceTable.getIndexCount() < identifier)
            info("Creating new Index[id={}], id is higher than the reference table contains.", identifier)

        val idx = referenceTable.fetchIndex(identifier)

        val buffer = dataFile.read(referenceTable.identifier, idx.identifier, idx.referenceSector, idx.sectorLength)

        idx.apply { decompressedBuffer = idx.decompress(this, buffer) }.apply { table.load() }

        return idx

    }

    override fun close()
    {
        path.fileSystem.close()
    }


}