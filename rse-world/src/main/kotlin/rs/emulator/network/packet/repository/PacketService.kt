package rs.emulator.network.packet.repository

import com.google.common.util.concurrent.AbstractIdleService
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.packet.api.PacketType
import rs.emulator.network.packet.atest.*
import rs.emulator.network.packet.listener.*

/**
 *
 * @author Chk
 */
class PacketService : AbstractIdleService(), KoinComponent
{

    private val packetRepository: PacketRepository = get()

    private fun construct()
    {

        packetRepository.putDecoder(89, KeyBoardEventDecoder(), packetType = PacketType.VARIABLE_SHORT, ignore = false, clazz = KeyBoardEventMessage::class,
            listener = KeyBoardEventListener()
        )

        packetRepository.putDecoders(opcodes = *intArrayOf(7, 61, 74, 86, 93), decoder = ObjActionDecoder(), length = 8, ignore = false, clazz = ObjActionMessage::class,
            listener = ObjActionListener()
        )

        packetRepository.putDecoder(46, WindowStatusDecoder(), length = 5, ignore = false, clazz = WindowStatusMessage::class,
            listener = WindowStatusHandler()
        )

        packetRepository.putDecoder(44, KeepAliveDecoder(), length = 0, ignore = true, clazz = KeepAliveMessage::class,
            listener = KeepAliveHandler()
        )

        packetRepository.putDecoders(opcodes = *intArrayOf(8, 26, 96), decoder = ExamineEntityDecoder(), length = 2, ignore = false, clazz = ExamineEntityMessage::class,
            listener = ExamineEntityListener()
        )

        packetRepository.putDecoder(14, MapBuildCompleteDecoder(), length = 0, ignore = false, clazz = MapBuildCompleteMessage::class,
            listener = MapBuildCompleteHandler()
        )

        packetRepository.putDecoder(69, ClientModDetectionDecoder(), length = 0, ignore = true, packetType = PacketType.VARIABLE_BYTE, clazz = ClientModDetectionMessage::class,
            listener = ClientModDetectionListener()
        )

        packetRepository.putDecoder(16, MousePositionUpdateDecoder(), length = 0, ignore = true, packetType = PacketType.VARIABLE_BYTE, clazz = MousePositionUpdateMessage::class,
            listener = MousePositionUpdateListener()
        )

        packetRepository.putDecoder(35, AppletFocusEventDecoder(), length = 1, ignore = true, clazz = AppletFocusEventMessage::class,
            listener = AppletFocusEventListener()
        )

        packetRepository.putDecoder(27, ConsoleCommandDecoder(), packetType = PacketType.VARIABLE_BYTE, ignore = false, clazz = ConsoleCommandMessage::class,
            listener = ConsoleCommandListener()
        )

        packetRepository.putDecoders(opcodes = *intArrayOf(13, 20, 23, 34, 62, 68, 76, 77, 79, 91), decoder = IfButtonDecoder(), length = 8, ignore = false, clazz = IfButtonMessage::class,
            listener = IfButtonListener()
        )

        packetRepository.putDecoder(3, MouseClickDecoder(), length = 6, ignore = false, clazz = MouseClickMessage::class,
            listener = MouseClickListener()
        )

        packetRepository.putDecoder(85, MouseIdleTickDecoder(), length = 0, ignore = true, clazz = MouseIdleTickMessage::class,
            listener = MouseIdleTickListener()
        )

        packetRepository.putEncoder(21, RebuildRegionEncoder(), packetType = PacketType.VARIABLE_SHORT, clazz = RebuildRegionMessage::class)

        packetRepository.putEncoder(6, IfOpenOverlayEncoder(), clazz = IfOpenOverlayMessage::class)

        packetRepository.putEncoder(76, UpdateDisplayWidgetsEncoder(), packetType = PacketType.VARIABLE_SHORT, clazz = UpdateDisplayWidgetsMessage::class)

        packetRepository.putEncoder(14, IfOpenSubEncoder(), clazz = IfOpenSubMessage::class)

        packetRepository.putEncoder(42, RunClientScriptEncoder(), packetType = PacketType.VARIABLE_SHORT, clazz = RunClientScriptMessage::class)

        packetRepository.putEncoder(66, VarpLargeEncoder(), clazz = VarpLargeMessage::class)

        packetRepository.putEncoder(32, VarpSmallEncoder(), clazz = VarpSmallMessage::class)

        packetRepository.putEncoder(45, IfCloseSubEncoder(), clazz = IfCloseSubMessage::class)

        packetRepository.putEncoder(62, IfMoveSubEncoder(), clazz = IfMoveSubMessage::class)

        packetRepository.putEncoder(84, GameMessageEncoder(), packetType = PacketType.VARIABLE_BYTE, clazz = GameMessageMessage::class)

        packetRepository.putEncoder(34, UpdateInventoryFullEncoder(), packetType = PacketType.VARIABLE_SHORT, clazz = UpdateInventoryFullMessage::class)

        packetRepository.putEncoder(1, UpdateInventoryPartialEncoder(), packetType = PacketType.VARIABLE_SHORT, clazz = UpdateInventoryPartialMessage::class)

        packetRepository.putEncoder(59, UpdatePlayerSyncEncoder(), packetType = PacketType.VARIABLE_SHORT, clazz = UpdatePlayerSyncMessage::class)

    }

    override fun startUp()
    {

        this.construct()

    }

    override fun shutDown()
    {



    }

}