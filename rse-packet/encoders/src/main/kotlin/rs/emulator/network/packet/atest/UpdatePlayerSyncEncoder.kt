package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.entity.player.Player
import rs.emulator.entity.player.update.mask.PlayerAppearanceMask
import rs.emulator.entity.player.update.sync.SyncInformation
import rs.emulator.entity.player.viewport.Viewport
import rs.emulator.entity.update.mask.UpdateMask
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author javatar
 */

class UpdatePlayerSyncEncoder : PacketEncoder<UpdatePlayerSyncMessage<Player>>() {
    override fun encode(message: UpdatePlayerSyncMessage<Player>, builder: GamePacketBuilder) {
        var flag = message.player.syncInfo.fetchMaskFlag()
        val maskBuilder = GamePacketBuilder()
        if (flag >= 0x100) {

            flag = flag or 16
            maskBuilder.put(DataType.BYTE, flag and 0xFF)
            maskBuilder.put(DataType.BYTE, flag shr 8)

        } else {
            maskBuilder.put(DataType.BYTE, flag and 0xFF)
        }
        builder.switchToBitAccess()
        localNsn0(message.player, builder, maskBuilder, true)
        localNsn1(message.player, builder, maskBuilder)
        globalNsn0(message.player, builder, true)
        globalNsn1(message.player, builder)
        //builder.switchToByteAccess()
        builder.putBytes(maskBuilder.byteBuf)
        println("size: " + builder.readableBytes)
    }

    private fun localNsn0(
        player: Player,
        builder: GamePacketBuilder,
        maskBuilder: GamePacketBuilder,
        inverse: Boolean = false
    ) = localNsn1(player, builder, maskBuilder, inverse)

    private fun localNsn1(
        player: Player,
        builder: GamePacketBuilder,
        maskBuilder: GamePacketBuilder,
        inverse: Boolean = false
    ) {

        val viewport = player.viewport

        var skipCount = 0

        viewport.localPlayers.forEach { (_, viewportPlayer) ->

            val syncInformation = viewportPlayer.syncInfo

            if (skipPlayer(syncInformation, inverse)) {

                return@forEach
            }

            if (skipCount > 0) {

                skipCount--

                syncInformation.setFlag(0x2)

                return@forEach

            }

            //todo: local scope players

            val updateRequired = syncInformation.requiresUpdate()

            if (updateRequired) {

                builder.switchToByteAccess()

                generateMaskBuffer(viewportPlayer, fetchMasks(), maskBuilder)

                builder.switchToBitAccess()

                builder.putBits(1, 1)

                builder.putBits(1, 1)

                builder.putBits(2, 0)

                builder.switchToByteAccess()

                syncInformation.resetFlag()

            } else {

                skipCount = generateSkipCount(viewport, builder, true, inverse)

                println("local skip count: $skipCount")

                syncInformation.setFlag(0x2)

            }

        }

    }

    private fun globalNsn0(player: Player, builder: GamePacketBuilder, inverse: Boolean = false) =
        globalNsn1(player, builder, inverse)

    private fun globalNsn1(player: Player, builder: GamePacketBuilder, inverse: Boolean = false) {

        var skipCount = 0

        val viewport = player.viewport

        viewport.globalPlayers.forEach { (_, viewportPlayer) ->

            val syncInformation = viewportPlayer.syncInfo

            if (skipPlayer(syncInformation, inverse)) return@forEach

            if (skipCount > 0) {

                skipCount--

                syncInformation.setFlag(0x2)

                return@forEach

            }

            //todo: update multi-player block

            skipCount = generateSkipCount(viewport, builder, false, inverse)

            println("global skip count 2: $skipCount")

            syncInformation.setFlag(0x2)

        }

    }

    private fun skipPlayer(syncInformation: SyncInformation, inverse: Boolean = false): Boolean {

        return if (inverse) syncInformation.hasFlag(0x1) else syncInformation.noFlag((0x1))

    }

    private fun generateSkipCount(
        viewport: Viewport,
        builder: GamePacketBuilder,
        local: Boolean = true,
        inverse: Boolean = false
    ): Int {

        var skipCount = 0

        val viewportCount = if (local) viewport.localPlayerCount else viewport.globalPlayerCount

        val playerMap = if (local) viewport.localPlayers else viewport.globalPlayers

        playerMap.forEach { (index, _) ->

            val nextIndex = if ((index + 1) == viewportCount) index + 1 else index

            val nextPlayer = playerMap[nextIndex]!! //todo: null-check required?

            val syncInformation = nextPlayer.syncInfo

            val skip = skipPlayer(syncInformation, inverse)

            if (skip) return@forEach

            skipCount++

        }

        builder.switchToBitAccess()

        skipCount = /*2048*/ 2046 - skipCount

        builder.putBits(1, 0)

        println("actual skip count : $skipCount")

        when {

            skipCount == 0 -> builder.putBits(2, 0)

            skipCount < 32 -> {
                builder.putBits(2, 1)
                builder.putBits(5, skipCount)
            }

            skipCount < 256 -> {
                builder.putBits(2, 2)
                builder.putBits(8, skipCount)
            }

            skipCount < 2048 -> {
                builder.putBits(2, 3)
                builder.putBits(11, skipCount)
            }

        }

        builder.switchToByteAccess()

        return skipCount

    }

    private fun generateMaskBuffer(entity: Player, masks: List<UpdateMask<Player>>, builder: GamePacketBuilder) {

        masks.forEach { mask -> mask.generate(entity = entity, builder = builder) }

    }

    private fun fetchMasks(): List<UpdateMask<Player>> {

        return listOf(

            PlayerAppearanceMask()

        ).sortedBy { it.fetchFlag().bit }

    }
}