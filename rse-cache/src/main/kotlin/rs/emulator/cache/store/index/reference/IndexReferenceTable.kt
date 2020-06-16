package rs.emulator.cache.store.index.reference

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

    lateinit var fileIds: Array<IntArray>

    lateinit var fileNameHashes: Array<IntArray>

    lateinit var groupNameHashTable: IntHashTable

    lateinit var fileNameHashTables: ArrayList<IntHashTable>

    private lateinit var ids: IntArray

    fun loadReference() = decodeHeader(referenceTable.value.fetchIndex(parent).fetchBuffer())

    fun fetchAllArchives() = (groupIds).map { lookup(it) }

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
        fileIds = Array(var6 + 1) { IntArray(0) }

        ids = IntArray(groupCount) { it.inc() - 1 }

        return var6

    }

    override fun loadTable(decompressed: Boolean): IndexReferenceTable
    {

        val idx = referenceTable.value.fetchIndex(parent)

        val reader = idx.decompress(idx, referenceTable.value.fetchIndex(idx.identifier).fetchBuffer())

        val var6 = decodeHeader(reader)

        if(namedArchive)
        {

            groupNameHashes = IntArray(var6 + 1)

            (0 until groupCount).forEach { group ->

                val nameHash = reader.getSigned(DataType.INT).toInt()

                groupNameHashes[groupIds[group]] = nameHash

                lookup(groupIds[group]).nameHash = nameHash

            }

        }

        (0 until groupCount).forEach { group ->

            val crc = reader.getSigned(DataType.INT).toInt()

            groupCrcs[groupIds[group]] = crc

            lookup(groupIds[group]).crc = crc

        }

        (0 until groupCount).forEach { group ->

            val version = reader.getSigned(DataType.INT).toInt()

            groupVersions[groupIds[group]] = version

            lookup(groupIds[group]).version = version

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

            fileIds[groupId] = IntArray(fileCount)

            (0 until fileCount).forEach { file ->

                fileId += if (protocol >= 7) reader.bigSmart else reader.getUnsigned(DataType.SHORT).toInt()

                fileIds[groupId][file] = fileId

                fileId = fileIds[groupId][file]

                if (fileId > fileIdOffset)
                    fileIdOffset = fileId

            }

            val archive = lookup(groupId)

            archive.named = namedArchive

            archive.entryCount = fileIds[groupId].size

            submitEntry(archive)

        }

        if (namedArchive)
        {

            fileNameHashes = Array(var6 + 1) { IntArray(0) }

            fileNameHashTables = ArrayList(var6 + 1)

            (0 until groupCount).forEach { group ->

                groupId = groupIds[group]

                fileCount = fileCounts[groupId]

                fileNameHashes[groupId] = IntArray(lookup(groupId).entryCount)

                (0 until fileCount).forEach { file ->

                    val nameHash = reader.getSigned(DataType.INT).toInt()

                    //fileNameHashes[groupId][fileIds[groupId][file]] = nameHash

                    lookup(groupId).table.lookup(file).nameHash = nameHash

                }

            }

        }

        (groupIds).forEach { group ->

            if(namedArchive)
            {

                val id = groupIds.indexOf(group)

                if(parent == 16 || parent == 19) return@forEach //todo: see wtf is wrong with this

                val hash = groupNameHashes[id]

                val archive = fetchAllArchives().firstOrNull { it.nameHash == hash }

                archive?.table?.loadTable(false)

            }
            else
                lookup(group).table.loadTable()

        }

        return this

    }

    override fun createEntry(identifier: Int): Archive = Archive(parent, identifier)

}