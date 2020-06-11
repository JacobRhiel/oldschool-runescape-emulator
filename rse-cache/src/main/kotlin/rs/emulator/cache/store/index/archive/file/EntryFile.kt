package rs.emulator.cache.store.index.archive.file

import rs.emulator.cache.store.file.StoreFile
import rs.emulator.cache.store.reference.table.IndependentReferenceTable

/**
 *
 * @author Chk
 */
class EntryFile(parent: Int,
                identifier: Int)
    : StoreFile(parent, identifier)
{

    override val table: IndependentReferenceTable<StoreFile>
        get() = TODO("Not yet implemented")

}