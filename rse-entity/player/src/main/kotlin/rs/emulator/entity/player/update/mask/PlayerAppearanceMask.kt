package rs.emulator.entity.player.update.mask

import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.writer.BufferedWriter
import rs.emulator.cache.store.VirtualFileStore
import rs.emulator.entity.material.containers.equipment
import rs.emulator.entity.player.Player
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.entity.update.flag.UpdateFlag
import rs.emulator.entity.update.mask.UpdateMask
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.utilities.koin.get

/**
 *
 * @author Chk
 */
class PlayerAppearanceMask : UpdateMask<Player>
{

    private val fileStore: VirtualFileStore = get()

    private val equipmentIndices = intArrayOf(8, 11, 4, 6, 9, 7, 10)

    private val testArr = IntArray(12) { 0 }

    override fun generate(entity: Player, builder: GamePacketBuilder)
    {

        val writer = BufferedWriter()

        writer.put(DataType.BYTE, if (entity.isFemale) 1 else 0)//gender
        writer.put(DataType.BYTE, entity.skullIcon)//skull icon
        writer.put(DataType.BYTE, entity.prayerIcon)//prayer icon

        val equipmentIndices = intArrayOf(8, 11, 4, 6, 9, 7, 10)

        val styles = intArrayOf(0, 0, 0, 0, 21, 0, 26, 38, 3, 33, 42, 14)

        val equipment = entity.equipment()

        for (index in 0 until 12)
        {

            /*if(index == 6)
            {
                val item = equipment.elements[EquipmentSlot.CHEST.slot]
                if(item.id != -1)// !== ItemData.EMPTY_WEARABLE)
                {
                    writer.put(DataType.BYTE, 0)
                    continue
                }
            }
            else if(index == 8)
            {
                val item = equipment.elements[EquipmentSlot.HEAD.slot]
                if(item.id != -1)// !== ItemData.EMPTY_WEARABLE)
                {

                    val itemDefinition: ObjMetaDataDefinition = definition().find(item.id)

                    val idkDefinition: IdentityKitDefinition = definition().find(styles[index])

                    //enums:
                    //886 - male beards
                    //889 - female hair - index 0 = bald
                    //882 - male hair - index 0 = bald

                    val enumDef: EnumDefinition = definition().find(if(entity.isFemale) 889 else 882)

                    if(itemDefinition.equipment.slot == "head" && idkDefinition.models.any { it == enumDef.intValues.first() })
                    {
                        writer.put(DataType.BYTE, 0)
                        continue
                    }

                }
            }
            else if(index == 11)
            {

                if(!entity.isFemale)
                {

                    val item = equipment.elements[EquipmentSlot.HEAD.slot]

                    val itemDefinition: ObjMetaDataDefinition = definition().find(item.id)

                    val idkDefinition: IdentityKitDefinition = definition().find(styles[index])

                    //enums:
                    //886 - male beards
                    //889 - female hair - index 0 = bald
                    //882 - male hair - index 0 = bald

                    val iDef: ObjDefinition = definition().find(item.id)

                    println("male head: " + iDef.maleHead)

                    val enumDef: EnumDefinition = definition().find(886)

                    if(itemDefinition.equipment.slot == "head" && !idkDefinition.models.any { it == enumDef.intValues.first() })
                    {
                        writer.put(DataType.BYTE, 0)
                        continue
                    }

                }

            }*/

            val item = equipment.elements[index]

            println("item: $item")

            if(item.id != -1)// != ItemData.EMPTY_WEARABLE)
            {
                println("adding id for index: $index")
                writer.put(DataType.SHORT, 0x200 + item.id)
            }
            else
            {
                if (styles[index] == 0)
                    writer.put(DataType.BYTE, 0)
                else
                {
                    writer.put(DataType.BYTE, 1)
                    writer.put(DataType.BYTE, styles[index])
                }
            }

        }

        for (i in 0 until 5)
        {
            val colors = arrayOf(3, 4, 2, 3, 2)
            writer.put(DataType.BYTE, colors[i])
        }

        val animations = intArrayOf(808, 823, 823, 820, 821, 822, 824)

        animations.forEach { anim ->
            writer.put(DataType.SHORT, anim)
        }

        writer.putString(entity.displayName())
        writer.put(DataType.BYTE, /*other.combatLevel*/3)
        writer.put(DataType.SHORT,0)//skill level
        writer.put(DataType.BYTE, 0)//is hidden

        builder.put(DataType.BYTE, DataTransformation.SUBTRACT, writer.byteBuf.readableBytes())

        builder.putBytes(DataTransformation.ADD, writer.byteBuf)

        entity.syncInfo.removeMaskFlag(PlayerUpdateFlag.APPEARANCE)

    }

    override fun fetchFlag(): UpdateFlag = PlayerUpdateFlag.APPEARANCE

    override fun shouldGenerate(entity: Player): Boolean = entity.syncInfo.hasMaskFlag(PlayerUpdateFlag.APPEARANCE)

}