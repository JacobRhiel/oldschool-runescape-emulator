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

        packetRepository.putDecoder(3, KeyBoardEventDecoder(), packetType = PacketType.VARIABLE_SHORT, ignore = false, clazz = KeyBoardEventMessage::class,
            listener = KeyBoardEventListener()
        )

        packetRepository.putDecoder(52, WindowStatusDecoder(), length = 5, ignore = false, clazz = WindowStatusMessage::class,
            listener = WindowStatusHandler()
        )

        packetRepository.putDecoder(30, KeepAliveDecoder(), length = 0, ignore = true, clazz = KeepAliveMessage::class,
            listener = KeepAliveHandler()
        )

        packetRepository.putDecoder(34, MapBuildCompleteDecoder(), length = 0, ignore = false, clazz = MapBuildCompleteMessage::class,
            listener = MapBuildCompleteHandler()
        )

        packetRepository.putDecoder(47, ClientModDetectionDecoder(), length = 0, ignore = true, packetType = PacketType.VARIABLE_BYTE, clazz = ClientModDetectionMessage::class,
            listener = ClientModDetectionListener()
        )

        packetRepository.putDecoder(74, MousePositionUpdateDecoder(), length = 0, ignore = true, packetType = PacketType.VARIABLE_BYTE, clazz = MousePositionUpdateMessage::class,
            listener = MousePositionUpdateListener()
        )

        packetRepository.putDecoder(54, AppletFocusEventDecoder(), length = 1, ignore = true, clazz = AppletFocusEventMessage::class,
            listener = AppletFocusEventListener()
        )

        packetRepository.putDecoder(93, ConsoleCommandDecoder(), packetType = PacketType.VARIABLE_BYTE, ignore = false, clazz = ConsoleCommandMessage::class,
            listener = ConsoleCommandListener()
        )

        packetRepository.putDecoder(57, IfButtonDecoder(), length = 8, ignore = false, clazz = IfButtonMessage::class,
            listener = IfButtonListener()
        )

        packetRepository.putDecoder(2, MouseClickDecoder(), length = 6, ignore = false, clazz = MouseClickMessage::class,
            listener = MouseClickListener()
        )

        packetRepository.putEncoder(17, RebuildRegionEncoder(), packetType = PacketType.VARIABLE_SHORT, clazz = RebuildRegionMessage::class)

        packetRepository.putEncoder(60, IfOpenOverlayEncoder(), clazz = IfOpenOverlayMessage::class)

        packetRepository.putEncoder(73, UpdateDisplayWidgetsEncoder(), packetType = PacketType.VARIABLE_SHORT, clazz = UpdateDisplayWidgetsMessage::class)

        packetRepository.putEncoder(64, IfOpenSubEncoder(), clazz = IfOpenSubMessage::class)

        packetRepository.putEncoder(49, RunClientScriptEncoder(), packetType = PacketType.VARIABLE_SHORT, clazz = RunClientScriptMessage::class)

        packetRepository.putEncoder(18, VarpLargeEncoder(), clazz = VarpLargeMessage::class)

        packetRepository.putEncoder(1, VarpSmallEncoder(), clazz = VarpSmallMessage::class)

        packetRepository.putEncoder(70, IfCloseSubEncoder(), clazz = IfCloseSubMessage::class)

        packetRepository.putEncoder(6, IfMoveSubEncoder(), clazz = IfMoveSubMessage::class)


    }

    override fun startUp()
    {

        this.construct()

    }

    override fun shutDown()
    {



    }

}