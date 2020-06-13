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

    private val lookup: Cache<Int, T> = Caffeine.newBuilder()
            .maximumSize(0xFFFF) //65535 default maximum size of any entry.
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .recordStats()
            .build()

    var loaded: Boolean = false

    var count: Int = 0
        get() = lookup.estimatedSize().toInt()

    abstract fun loadTable() : IndependentReferenceTable<T>

    abstract fun createEntry(identifier: Int) : T

    internal fun load()
    {

        println("loading table")

        loadTable()

        loaded = true

    }

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
        println(javaClass.simpleName)
        println(this)
        submitEntry(identifier)
    }

    internal fun copyBufferToReader(reader: BufferedReader, length: Int) : BufferedReader
    {

        val buffer = ByteArray(length)

        reader.readBytes(buffer, 0, length)

        return BufferedReader(Unpooled.wrappedBuffer(buffer))

    }

}