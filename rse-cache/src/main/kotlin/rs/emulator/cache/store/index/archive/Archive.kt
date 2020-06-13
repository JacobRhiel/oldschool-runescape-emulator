package rs.emulator.cache.store.index.archive

import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.store.file.StoreFile
import rs.emulator.cache.store.index.archive.file.EntryFile
import rs.emulator.cache.store.index.archive.reference.ArchiveReferenceTable
import rs.emulator.cache.store.index.reference.IndexReferenceTable
import rs.emulator.cache.store.reference.table.IndependentReferenceTable

/**
 *
 * @author Chk
 */
class Archive(
        parent: Int,
        identifier: Int
) : StoreFile(parent, identifier)
{

    var nameHash: Int = 0

    var crc: Int = 0

    var version: Int = 0

    var entryCount: Int = 0

    override val table: ArchiveReferenceTable = ArchiveReferenceTable(parent, identifier)

    fun fetchEntry(identifier: Int) = table.lookup(identifier)

}