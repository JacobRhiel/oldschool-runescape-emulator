package rs.emulator.network.packet.repository

import rs.emulator.network.packet.ActionType
import rs.emulator.network.packet.PacketType
import rs.emulator.network.packet.atest.*
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.listener.*
import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.network.packet.metadata.DecodingPacketMetaData
import rs.emulator.network.packet.metadata.EncodingPacketMetaData
import kotlin.reflect.KClass

/**
 *
 * @author Chk
 */
class PacketRepository(private val encoders: HashMap<KClass<out GamePacketMessage>, EncodingPacketMetaData> = hashMapOf(),
                       private val decoders: HashMap<KClass<out GamePacketMessage>, DecodingPacketMetaData> = hashMapOf()
)
{

    fun fetchDecoder(opcode: Int) = decoders.values.firstOrNull { it.opcode == opcode } ?: throw Error("Unknown decoder for opcode: $opcode")

    fun <T : GamePacketMessage> fetchEncoder(clazz: KClass<T>) = encoders[clazz] ?: throw Error("Unknown encoder")

    fun construct() : PacketRepository
    {

        putDecoder(3, KeyBoardEventDecoder(), packetType = PacketType.VARIABLE_SHORT, ignore = false, clazz = KeyBoardEventMessage::class,
            listener = KeyBoardEventListener()
        )

        putDecoder(52, WindowStatusDecoder(), length = 5, ignore = false, clazz = WindowStatusMessage::class,
            listener = WindowStatusHandler()
        )

        putDecoder(30, KeepAliveDecoder(), length = 0, ignore = true, clazz = KeepAliveMessage::class,
            listener = KeepAliveHandler()
        )

        putDecoder(34, MapBuildCompleteDecoder(), length = 0, ignore = false, clazz = MapBuildCompleteMessage::class,
            listener = MapBuildCompleteHandler()
        )

        putDecoder(47, ClientModDetectionDecoder(), length = 0, ignore = true, packetType = PacketType.VARIABLE_BYTE, clazz = ClientModDetectionMessage::class,
            listener = ClientModDetectionListener()
        )

        putDecoder(74, MousePositionUpdateDecoder(), length = 0, ignore = true, packetType = PacketType.VARIABLE_BYTE, clazz = MousePositionUpdateMessage::class,
            listener = MousePositionUpdateListener()
        )

        putDecoder(54, AppletFocusEventDecoder(), length = 1, ignore = true, clazz = AppletFocusEventMessage::class,
            listener = AppletFocusEventListener()
        )

        putEncoder(17, RebuildRegionEncoder(), packetType = PacketType.VARIABLE_SHORT, clazz = RebuildRegionMessage::class)

        putEncoder(60, IfOpenOverlayEncoder(), clazz = IfOpenOverlayMessage::class)

        return this

    }

    fun <T : GamePacketMessage> putEncoder(opcode: Int, encoder: PacketEncoder<out GamePacketMessage>, packetType: PacketType = PacketType.FIXED, clazz: KClass<out T>) = encoders.computeIfAbsent(clazz) { EncodingPacketMetaData(opcode, encoder, packetType) }

    fun <T : GamePacketMessage> putDecoder(opcode: Int, decoder: PacketDecoder<out GamePacketMessage>, listener: GamePacketListener<out GamePacketMessage>, packetType: PacketType = PacketType.FIXED, actionType: ActionType = ActionType.NONE, length: Int = 0, ignore: Boolean = false, clazz: KClass<out T>)
            = decoders.computeIfAbsent(clazz) { DecodingPacketMetaData(opcode, decoder, listener, packetType, actionType, length, ignore) }

}