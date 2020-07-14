package rs.emulator.network.packet.repository

import com.google.common.util.concurrent.AbstractIdleService
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.network.packet.atest.*
import rs.emulator.network.packet.atest.outgoing.*
import rs.emulator.network.packet.listener.*
import rs.emulator.packet.api.PacketType

/**
 *
 * @author Chk
 */
class PacketService : AbstractIdleService(), KoinComponent {

    private val packetRepository: PacketRepository = get()

    private fun construct() {

        packetRepository.putDecoder(
            89,
            KeyBoardEventDecoder(),
            packetType = PacketType.VARIABLE_SHORT,
            ignore = false,
            clazz = KeyBoardEventMessage::class,
            listener = KeyBoardEventListener()
        )

        packetRepository.putDecoders(
            opcodes = *intArrayOf(7, 61, 74, 86, 93),
            decoder = ObjActionDecoder(),
            length = 8,
            ignore = false,
            clazz = ObjActionMessage::class,
            listener = ObjActionListener()
        )

        packetRepository.putDecoder(
            46, WindowStatusDecoder(), length = 5, ignore = false, clazz = WindowStatusMessage::class,
            listener = WindowStatusHandler()
        )

        packetRepository.putDecoder(
            44, KeepAliveDecoder(), length = 0, ignore = true, clazz = KeepAliveMessage::class,
            listener = KeepAliveHandler()
        )

        packetRepository.putDecoders(
            opcodes = *intArrayOf(8, 26, 96),
            decoder = ExamineEntityDecoder(),
            length = 2,
            ignore = false,
            clazz = ExamineEntityMessage::class,
            listener = ExamineEntityListener()
        )

        packetRepository.putDecoder(
            14, MapBuildCompleteDecoder(), length = 0, ignore = false, clazz = MapBuildCompleteMessage::class,
            listener = MapBuildCompleteHandler()
        )

        packetRepository.putDecoder(
            69,
            ClientModDetectionDecoder(),
            length = 0,
            ignore = true,
            packetType = PacketType.VARIABLE_BYTE,
            clazz = ClientModDetectionMessage::class,
            listener = ClientModDetectionListener()
        )

        packetRepository.putDecoder(
            16,
            MousePositionUpdateDecoder(),
            length = 0,
            ignore = true,
            packetType = PacketType.VARIABLE_BYTE,
            clazz = MousePositionUpdateMessage::class,
            listener = MousePositionUpdateListener()
        )

        packetRepository.putDecoder(
            35, AppletFocusEventDecoder(), length = 1, ignore = true, clazz = AppletFocusEventMessage::class,
            listener = AppletFocusEventListener()
        )

        packetRepository.putDecoder(
            27,
            ConsoleCommandDecoder(),
            packetType = PacketType.VARIABLE_BYTE,
            ignore = false,
            clazz = ConsoleCommandMessage::class,
            listener = ConsoleCommandListener()
        )

        packetRepository.putDecoders(
            opcodes = *intArrayOf(13, 20, 23, 34, 62, 68, 76, 77, 79, 91),
            decoder = IfButtonDecoder(),
            length = 8,
            ignore = false,
            clazz = IfButtonMessage::class,
            listener = IfButtonListener()
        )

        packetRepository.putDecoder(
            3, MouseClickDecoder(), length = 6, ignore = false, clazz = MouseClickMessage::class,
            listener = MouseClickListener()
        )

        packetRepository.putDecoder(
            85, MouseIdleTickDecoder(), length = 0, ignore = true, clazz = MouseIdleTickMessage::class,
            listener = MouseIdleTickListener()
        )

        packetRepository.putEncoder(
            21,
            RebuildRegionEncoder(),
            packetType = PacketType.VARIABLE_SHORT,
            clazz = RebuildRegionMessage::class
        )

        packetRepository.putEncoder(6, IfOpenOverlayEncoder(), clazz = IfOpenOverlayMessage::class)

        packetRepository.putEncoder(
            76,
            UpdateDisplayWidgetsEncoder(),
            packetType = PacketType.VARIABLE_SHORT,
            clazz = UpdateDisplayWidgetsMessage::class
        )

        packetRepository.putEncoder(14, IfOpenSubEncoder(), clazz = IfOpenSubMessage::class)

        packetRepository.putEncoder(
            42,
            RunClientScriptEncoder(),
            packetType = PacketType.VARIABLE_SHORT,
            clazz = RunClientScriptMessage::class
        )

        packetRepository.putEncoder(
            66,
            VarpLargeEncoder(),
            clazz = VarpLargeMessage::class
        )

        packetRepository.putEncoder(
            32,
            VarpSmallEncoder(),
            clazz = VarpSmallMessage::class
        )

        packetRepository.putEncoder(
            45,
            IfCloseSubEncoder(),
            clazz = IfCloseSubMessage::class
        )

        packetRepository.putEncoder(
            62,
            IfMoveSubEncoder(),
            clazz = IfMoveSubMessage::class
        )

        packetRepository.putEncoder(
            84,
            GameMessageEncoder(),
            packetType = PacketType.VARIABLE_BYTE,
            clazz = GameMessageMessage::class
        )

        packetRepository.putEncoder(
            34,
            UpdateInventoryFullEncoder(),
            packetType = PacketType.VARIABLE_SHORT,
            clazz = UpdateInventoryFullMessage::class
        )

        packetRepository.putEncoder(
            1,
            UpdateInventoryPartialEncoder(),
            packetType = PacketType.VARIABLE_SHORT,
            clazz = UpdateInventoryPartialMessage::class
        )

        packetRepository.putEncoder(
            59,
            UpdatePlayerSyncEncoder(),
            packetType = PacketType.VARIABLE_SHORT,
            clazz = UpdatePlayerSyncMessage::class
        )

        packetRepository.putEncoder(
            12,
            UpdateSkillEncoder(),
            clazz = UpdateSkillMessage::class
        )

        packetRepository.putEncoder(
            2,
            PrivateMessageEncoder(),
            packetType = PacketType.VARIABLE_SHORT,
            clazz = PrivateMessageMessage::class
        )

        packetRepository.putEncoder(
            71,
            PrivateChatFilterEncoder(),
            clazz = PrivateChatFilterMessage::class
        )

        packetRepository.putEncoder(
            26,
            UpdateRunEnergyEncoder(),
            clazz = UpdateRunEnergyMessage::class
        )

        packetRepository.putEncoder(
            77,
            PlayerOptionEncoder(),
            packetType = PacketType.VARIABLE_BYTE,
            clazz = PlayerOptionMessage::class
        )

        packetRepository.putEncoder(
            27,
            UpdateSystemRebootEncoder(),
            clazz = UpdateSystemRebootMessage::class
        )

        packetRepository.putEncoder(
            47,
            ZeroSizeEncoder(),
            clazz = CameraResetMessage::class
        )
        packetRepository.putEncoder(
            25,
            ZeroSizeEncoder(),
            clazz = LogoutFullMessage::class
        )

        packetRepository.putEncoder(
            53,
            ZeroSizeEncoder(),
            clazz = SyncClientVarCacheMessage::class
        )

        packetRepository.putEncoder(
            56,
            ZeroSizeEncoder(),
            clazz = ResetClientVarCacheMessage::class
        )

        packetRepository.putEncoder(
            37,
            ZeroSizeEncoder(),
            clazz = FriendsListLoadedMessage::class
        )

        packetRepository.putEncoder(
            72,
            ZeroSizeEncoder(),
            clazz = TriggerOnDialogAbortMessage::class
        )

        packetRepository.putEncoder(
            74,
            ZeroSizeEncoder(),
            clazz = ResetAnimationsMessage::class
        )

        packetRepository.putEncoder(
            28,
            HintArrowEncoder(),
            clazz = HintArrowMessage::class
        )

        packetRepository.putEncoder(
            7,
            IfSetModelEncoder(),
            clazz = IfSetModelMessage::class
        )

        packetRepository.putEncoder(
            61,
            IfSetAnimationEncoder(),
            clazz = IfSetAnimationMessage::class
        )

        packetRepository.putEncoder(
            69,
            ClientStatisticsEncoder(),
            clazz = ClientStatisticsMessage::class
        )

        packetRepository.putEncoder(
            68,
            ChatFilterSettingEncoder(),
            clazz = ChatFilterSettingMessage::class
        )

        packetRepository.putEncoder(
            6,
            IfOpenTopEncoder(),
            clazz = IfOpenTopMessage::class
        )

        packetRepository.putEncoder(
            65,
            IfSetObjEncoder(),
            clazz = IfSetObjMessage::class
        )

        packetRepository.putEncoder(
            4,
            IfSetWidgetItemsEncoder(),
            clazz = IfSetWidgetItemsMessage::class
        )

        packetRepository.putEncoder(
            57,
            IfSetHideEncoder(),
            clazz = IfSetHideMessage::class
        )

        packetRepository.putEncoder(
            55,
            IfSetTextEncoder(),
            packetType = PacketType.VARIABLE_SHORT,
            clazz = IfSetTextMessage::class
        )

        packetRepository.putEncoder(
            51,
            PlayMIDISongEncoder(),
            clazz = PlayMIDISongMessage::class
        )

        packetRepository.putEncoder(
            48,
            IfSetScrollPositionEncoder(),
            clazz = IfSetScrollPositionMessage::class
        )

        packetRepository.putEncoder(
            82,
            WorldTransferEncoder(),
            packetType = PacketType.VARIABLE_BYTE,
            clazz = WorldTransferMessage::class
        )

        packetRepository.putEncoder(
            44,
            IfSetPlayerChatHeadEncoder(),
            clazz = IfSetPlayerChatHeadMessage::class
        )

        packetRepository.putEncoder(
            73,
            OpenURLEncoder(),
            clazz = OpenURLMessage::class
        )

        packetRepository.putEncoder(
            41,
            DisconnectedEncoder(),
            clazz = DisconnectedMessage::class
        )

        packetRepository.putEncoder(
            43,
            IfSetNpcHeadEncoder(),
            clazz = IfSetNpcHeadMessage::class
        )

        packetRepository.putEncoder(
            35,
            OculusOrbToggleEncoder(),
            clazz = OculusOrbToggleMessage::class
        )

        packetRepository.putEncoder(
            19,
            UpdatePlayerWeightEncoder(),
            clazz = UpdatePlayerWeightMessage::class
        )

        packetRepository.putEncoder(
            22,
            PlayMIDIJingleEncoder(),
            clazz = PlayMIDIJingleMessage::class
        )

        packetRepository.putEncoder(
            8,
            GrandExchangeOfferEncoder(),
            clazz = GrandExchangeOfferMessage::class
        )

    }

    override fun startUp() {

        this.construct()

    }

    override fun shutDown() {


    }

}