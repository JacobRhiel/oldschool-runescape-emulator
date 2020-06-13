package rs.emulator.cache.store.index.reference

import it.unimi.dsi.fastutil.ints.Int2ReferenceArrayMap
import org.koin.core.KoinComponent
import org.koin.core.inject
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.store.index.archive.Archive
import rs.emulator.cache.store.reference.ReferenceTable
import rs.emulator.cache.store.reference.table.IndependentReferenceTable
import rs.emulator.cache.store.security.crc.CrcTable
import rs.emulator.cache.store.security.namehash.IntHashTable

/**
 *
 * @author Chk
 */
class IndexReferenceTable(parent: Int)
    : KoinComponent, IndependentReferenceTable<Archive>(parent)
{

    private val referenceTable = inject<ReferenceTable>()

    var protocol: Int = 6

    var version: Int = 0

    var namedArchive: Boolean = true

    var hash: Int = 0

    var groupCount = 0

    lateinit var groupIds: IntArray

    lateinit var groupNameHashes: IntArray

    lateinit var groupCrcs: IntArray

    lateinit var groupVersions: IntArray

    lateinit var fileCounts: IntArray

    lateinit var fileIds: Int2ReferenceArrayMap<IntArray>

    lateinit var fileNameHashes: Int2ReferenceArrayMap<IntArray>

    lateinit var groupNameHashTable: IntHashTable

    lateinit var fileNameHashTables: ArrayList<IntHashTable>

    private lateinit var ids: IntArray

    fun loadReference() = decodeHeader(referenceTable.value.fetchIndex(parent).decompressedBuffer.copy())

    private fun decodeHeader(reader: BufferedReader): Int
    {

        hash = CrcTable.fetchIdxHash(reader.toArray(), reader.readableBytes)

        protocol = reader.getUnsigned(DataType.BYTE).toInt()

        require(!(protocol < 5 || protocol > 7)) { "Unsupported protocol: $protocol" }

        if(protocol >= 6)
            version = reader.getSigned(DataType.INT).toInt()

        namedArchive = (reader.getUnsigned(DataType.BYTE).toInt() != 0)

        groupCount = if(protocol >= 7) reader.bigSmart else reader.getUnsigned(DataType.SHORT).toInt()

        groupIds = IntArray(groupCount)

        var groupId = 0

        var var6 = -1

        (0 until groupCount).forEach { group ->

            groupId += if(protocol >= 7) reader.bigSmart else reader.getUnsigned(DataType.SHORT).toInt()

            groupIds[group] = groupId

            if(groupIds[group] > var6)
                var6 = groupIds[group]

        }

        groupCrcs = IntArray(var6 + 1)
        groupVersions = IntArray(var6 + 1)
        fileCounts = IntArray(var6 + 1)
        fileIds = Int2ReferenceArrayMap(var6 + 1)

        ids = IntArray(groupCount) { it.inc() - 1 }

        println("archive count: $groupCount")

        return var6

    }

    override fun loadTable(): IndexReferenceTable
    {

        val idx = referenceTable.value.fetchIndex(parent)

        val reader = idx.decompressedBuffer.copy()

        val var6 = decodeHeader(reader)

        if(namedArchive)
        {

            groupNameHashes = IntArray(var6 + 1)

            (0 until groupCount).forEach { group ->

                groupNameHashes[groupIds[group]] = reader.getSigned(DataType.INT).toInt()

                groupNameHashTable = IntHashTable(groupNameHashes)

            }

        }

        (0 until groupCount).forEach { group ->

            groupCrcs[groupIds[group]] = reader.getSigned(DataType.INT).toInt()

        }

        (0 until groupCount).forEach { group ->

            groupVersions[groupIds[group]] = reader.getSigned(DataType.INT).toInt()

        }

        (0 until groupCount).forEach { group ->

            fileCounts[groupIds[group]] = reader.getUnsigned(DataType.SHORT).toInt()

        }

        var groupId: Int

        var fileCount: Int

        var fileId = 0

        var fileIdOffset = -1

        (0 until groupCount).forEach { group ->

            groupId = groupIds[group]

            fileCount = fileCounts[groupId]

            println("files count: " + fileIds.size)

            fileIds[groupId] = IntArray(fileCount)

            println("count: " + fileIds[groupId].size)

            (0 until fileCount).forEach { file ->

                fileId += if (protocol >= 7) reader.bigSmart else reader.getUnsigned(DataType.SHORT).toInt()

                fileIds[groupId][file] = fileId

                fileId = fileIds[groupId][file]

                if (fileId > fileIdOffset)
                    fileIdOffset = fileId

            }

            val archive = lookup(groupId)

            archive.entryCount = fileIds[groupId].size

            submitEntry(archive)

        }

        if (namedArchive)
        {

            fileNameHashes = Int2ReferenceArrayMap(var6 + 1)

            fileNameHashTables = ArrayList(var6 + 1)

            (0 until groupCount).forEach { group ->

                groupId = groupIds[group]

                fileCount = fileCounts[groupId]

                fileNameHashes[groupId] = IntArray(lookup(groupId).entryCount)

                (0 until fileCount).forEach {  file ->

                    fileNameHashes[groupId][fileIds[groupId][file]] = reader.getSigned(DataType.INT).toInt()

                }

                fileNameHashTables[groupId] = IntHashTable((fileNameHashes[groupId]))

            }

        }

        return this

    }

    override fun createEntry(identifier: Int): Archive = Archive(parent, identifier)

}