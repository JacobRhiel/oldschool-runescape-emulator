package rs.emulator.cache.store.reference.table

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import io.netty.buffer.Unpooled
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap
import org.koin.core.KoinComponent
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.store.file.StoreFile
import rs.emulator.utilities.caffeine.getOrPut
import java.util.concurrent.TimeUnit

/**
 *
 * @author Chk
 */
abstract class IndependentReferenceTable<T : StoreFile>(
        val parent: Int
)
{

    val lookup = Int2ObjectAVLTreeMap<T>()

    var count: Int = 0
        get() = lookup.size

    abstract fun loadTable(decompressed: Boolean = true) : IndependentReferenceTable<T>

    abstract fun createEntry(identifier: Int) : T

    internal fun load() = loadTable()

    fun submitEntry(entry: T) : T = lookup.getOrPut(entry.identifier) {
        entry
    }

    fun submitEntry(identifier: Int) : T = lookup.getOrPut(identifier) {
        submitEntry(createEntry(identifier))
    }

    fun lookup(entry: T) : T = lookup.getOrPut(entry.identifier) {
        lookup(entry.identifier)
    }

    fun lookup(identifier: Int) : T = lookup.getOrPut(identifier) {
        submitEntry(identifier)
    }

    internal fun copyBufferToReader(reader: BufferedReader, length: Int) : BufferedReader
    {

        val buffer = ByteArray(length)

        reader.readBytes(buffer, 0, length)

        return BufferedReader(Unpooled.wrappedBuffer(buffer))

    }

}