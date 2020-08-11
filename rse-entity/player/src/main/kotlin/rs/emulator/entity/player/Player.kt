package rs.emulator.entity.player

import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.toObservable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.dusk.engine.path.Finder
import rs.emulator.database.service.JDBCPoolingService
import rs.emulator.entity.actor.Actor
import rs.emulator.entity.actor.attributes.ActorAttributes
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.messages.AbstractMessageHandler
import rs.emulator.entity.actor.player.messages.IMessages
import rs.emulator.entity.actor.player.widgets.WidgetViewport
import rs.emulator.entity.details.PlayerDetails
import rs.emulator.entity.material.containers.ItemContainerManager
import rs.emulator.entity.material.containers.impl.Equipment
import rs.emulator.entity.material.containers.impl.Inventory
import rs.emulator.entity.material.containers.inventory
import rs.emulator.entity.player.chat.PublicChatText
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.entity.player.update.sync.SyncInformation
import rs.emulator.entity.player.viewport.Viewport
import rs.emulator.entity.skills.Skill
import rs.emulator.entity.skills.SkillManager
import rs.emulator.network.packet.message.outgoing.*
import rs.emulator.packet.api.IPacketMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.LoginActionFactory
import rs.emulator.region.WorldCoordinate
import rs.emulator.region.coordinate.Coordinate
import rs.emulator.skills.SkillAttributes
import rs.emulator.world.World
import java.util.concurrent.atomic.AtomicLong

@ExperimentalCoroutinesApi
class Player(
    index: Int,
    val outgoingPackets: PublishProcessor<IPacketMessage>,
    private val disposable: CompositeDisposable,
    override val details: PlayerDetails
) : Actor(index), IPlayer,
    KoinComponent, Disposable by disposable, DisposableContainer by disposable {

    val world: World = get()

    val viewport = Viewport(this)

    val syncInfo = SyncInformation().apply { this.addMaskFlag(PlayerUpdateFlag.APPEARANCE) }

    val messageHandler = MessageHandler(this)

    val idleMouseTicks = AtomicLong(0L)

    var pendingTeleport: Coordinate? = null

    var pendingPublicChatMessage: PublicChatText? = null

    override val searchPattern: Finder = pathFinder.bfs

    @ExperimentalCoroutinesApi
    override val skillManager: SkillManager = SkillManager()

    override val widgetViewport = WidgetViewport(this)

    override val actorAttributes: ActorAttributes = ActorAttributes()

    override val containerManager: ItemContainerManager = ItemContainerManager()

    override var energy: Int by actorAttributes.Int(100).markPersistent().apply {
        add(this.changeListener.subscribe {
            outgoingPackets.offer(UpdateRunEnergyMessage(it))
        })
    }

    override var isFemale: Boolean by actorAttributes.Boolean().markPersistent()

    override var skullIcon: Int by actorAttributes.Int(-1).markPersistent()

    override var prayerIcon: Int by actorAttributes.Int(-1)

    var pendingAnimation: Int = -1

    var pendingGraphic: Int = -1

    var pendingGraphicHeight: Int = 0

    var pendingGraphicDelay: Int = 0

    fun onLogin() {

        //TODO - dispose of overlay on logout (on second thought since overlay is const don't do this)

        outgoingPackets.offer(
            RebuildRegionMessage(
                true,
                1,
                x = coordinate.x,
                z = coordinate.z,
                tileHash = coordinate.as30BitInteger
            )
        )

        outgoingPackets.offer(VarpSmallMessage(18, 1))
        outgoingPackets.offer(VarpLargeMessage(20, 59899954))
        outgoingPackets.offer(VarpLargeMessage(21, 102084876))
        outgoingPackets.offer(VarpLargeMessage(22, 302317568))
        outgoingPackets.offer(VarpLargeMessage(23, 6619200))
        outgoingPackets.offer(VarpLargeMessage(24, -2146434396))
        outgoingPackets.offer(VarpSmallMessage(25, 2))
        outgoingPackets.offer(VarpSmallMessage(101, 0))
        outgoingPackets.offer(VarpSmallMessage(153, -1))
        outgoingPackets.offer(VarpSmallMessage(166, 2))
        outgoingPackets.offer(VarpSmallMessage(167, 0))
        outgoingPackets.offer(VarpSmallMessage(168, 4))
        outgoingPackets.offer(VarpSmallMessage(169, 4))
        outgoingPackets.offer(VarpSmallMessage(170, 0))
        outgoingPackets.offer(VarpSmallMessage(171, 0))
        outgoingPackets.offer(VarpSmallMessage(173, 1))
        outgoingPackets.offer(VarpLargeMessage(281, 1000))
        outgoingPackets.offer(VarpLargeMessage(284, 60001))
        outgoingPackets.offer(VarpSmallMessage(287, 1))
        outgoingPackets.offer(VarpLargeMessage(300, 1000))
        outgoingPackets.offer(VarpLargeMessage(304, 50000000))
        outgoingPackets.offer(VarpLargeMessage(311, 16777216))
        outgoingPackets.offer(VarpSmallMessage(346, 2))
        outgoingPackets.offer(VarpSmallMessage(406, 20))
        outgoingPackets.offer(VarpSmallMessage(427, 1))
        outgoingPackets.offer(VarpSmallMessage(447, -1))
        outgoingPackets.offer(VarpLargeMessage(449, 2097152))
        outgoingPackets.offer(VarpLargeMessage(464, 1073741824))
        outgoingPackets.offer(VarpLargeMessage(518, 536870912))
        outgoingPackets.offer(VarpSmallMessage(520, 1))
        outgoingPackets.offer(VarpLargeMessage(553, -2147483648))
        outgoingPackets.offer(VarpSmallMessage(664, -1))
        outgoingPackets.offer(VarpLargeMessage(721, 2048))
        outgoingPackets.offer(VarpLargeMessage(788, 128))
        outgoingPackets.offer(VarpLargeMessage(810, 33554432))
        outgoingPackets.offer(VarpSmallMessage(843, 9))
        outgoingPackets.offer(VarpSmallMessage(849, -1))
        outgoingPackets.offer(VarpSmallMessage(850, -1))
        outgoingPackets.offer(VarpSmallMessage(851, -1))
        outgoingPackets.offer(VarpSmallMessage(852, -1))
        outgoingPackets.offer(VarpSmallMessage(853, -1))
        outgoingPackets.offer(VarpSmallMessage(854, -1))
        outgoingPackets.offer(VarpSmallMessage(855, -1))
        outgoingPackets.offer(VarpSmallMessage(856, -1))
        outgoingPackets.offer(VarpLargeMessage(867, 1034))
        outgoingPackets.offer(VarpSmallMessage(872, 4))
        outgoingPackets.offer(VarpLargeMessage(904, 277))
        outgoingPackets.offer(VarpLargeMessage(913, 4194304))
        outgoingPackets.offer(VarpLargeMessage(1009, 2097152))
        outgoingPackets.offer(VarpLargeMessage(1010, 2048))
        outgoingPackets.offer(VarpLargeMessage(1017, 8192))
        outgoingPackets.offer(VarpLargeMessage(1021, 2080))
        outgoingPackets.offer(VarpLargeMessage(1042, 9856))
        outgoingPackets.offer(VarpLargeMessage(1050, 4096))
        outgoingPackets.offer(VarpLargeMessage(1052, 1073741824))
        outgoingPackets.offer(VarpLargeMessage(1055, -2147473856))
        outgoingPackets.offer(VarpSmallMessage(1065, -1))
        outgoingPackets.offer(VarpLargeMessage(1067, -784859136))
        outgoingPackets.offer(VarpSmallMessage(1074, 0))
        outgoingPackets.offer(VarpSmallMessage(1075, -1))
        outgoingPackets.offer(VarpSmallMessage(1107, 0))
        outgoingPackets.offer(VarpSmallMessage(1141, 32))
        outgoingPackets.offer(VarpSmallMessage(1151, -1))
        outgoingPackets.offer(VarpLargeMessage(1224, 172395585))
        outgoingPackets.offer(VarpLargeMessage(1225, 379887846))
        outgoingPackets.offer(VarpLargeMessage(1226, 67207180))
        outgoingPackets.offer(VarpSmallMessage(1227, 48))
        outgoingPackets.offer(VarpSmallMessage(1306, 0))
        outgoingPackets.offer(VarpSmallMessage(1427, -1))
        outgoingPackets.offer(VarpLargeMessage(1429, 30720))
        outgoingPackets.offer(VarpSmallMessage(1666, 80))
        outgoingPackets.offer(VarpSmallMessage(1683, -1))
        outgoingPackets.offer(VarpSmallMessage(1706, 2))
        outgoingPackets.offer(VarpLargeMessage(1737, -2147483648))
        outgoingPackets.offer(VarpSmallMessage(1740, -1))
        outgoingPackets.offer(VarpSmallMessage(2657, -1))
        outgoingPackets.offer(VarpSmallMessage(2659, -1))
        outgoingPackets.offer(VarpSmallMessage(2661, -1))
        outgoingPackets.offer(VarpSmallMessage(2663, -1))
        outgoingPackets.offer(VarpSmallMessage(2674, -1))
        outgoingPackets.offer(VarpLargeMessage(2686, 1000))

        outgoingPackets.offer(RunClientScriptMessage(2498, 0, 0, 0))
        outgoingPackets.offer(RunClientScriptMessage(2498, 0, 0, 0))
        outgoingPackets.offer(RunClientScriptMessage(233, 24772664, 40590, 5, 240, 117, 1936, 0, 1200, -1))
        outgoingPackets.offer(RunClientScriptMessage(233, 24772665, 40587, -10, 120, 105, 1747, 0, 1120, 8748))
        outgoingPackets.offer(RunClientScriptMessage(3092, 2243, "Subscribe Now"))

        outgoingPackets.offer(IfOpenOverlayMessage(165))

        outgoingPackets.offer(IfOpenSubMessage(165, 1, 162, 1))
        outgoingPackets.offer(IfOpenSubMessage(165, 2, 651, 1))
        outgoingPackets.offer(IfOpenSubMessage(165, 25, 163, 1))
        outgoingPackets.offer(IfOpenSubMessage(165, 26, 160, 1))
        outgoingPackets.offer(IfOpenSubMessage(165, 6, 122, 1))
        outgoingPackets.offer(IfOpenSubMessage(165, 29, 378, 0))

        outgoingPackets.offer(RunClientScriptMessage(1080, "https://osrs.game/UpdateBroadcast"))

        outgoingPackets.offer(RunClientScriptMessage(828, 0))

        outgoingPackets.offer(IfOpenSubMessage(165, 11, 320, 1))
        outgoingPackets.offer(IfOpenSubMessage(165, 12, 629, 1))

        outgoingPackets.offer(RunClientScriptMessage(828, 0))

        outgoingPackets.offer(IfOpenSubMessage(629, 33, 76, 1))
        outgoingPackets.offer(IfOpenSubMessage(165, 13, 149, 1))
        outgoingPackets.offer(IfOpenSubMessage(165, 14, 387, 1))
        outgoingPackets.offer(IfOpenSubMessage(165, 15, 541, 1))
        outgoingPackets.offer(IfOpenSubMessage(165, 16, 218, 1))
        outgoingPackets.offer(IfOpenSubMessage(165, 19, 429, 1))

        outgoingPackets.offer(RunClientScriptMessage(2498, 0, 0, 0))

        outgoingPackets.offer(IfOpenSubMessage(165, 18, 109, 1))
        outgoingPackets.offer(IfOpenSubMessage(165, 20, 182, 1))
        outgoingPackets.offer(IfOpenSubMessage(165, 21, 261, 1))
        outgoingPackets.offer(IfOpenSubMessage(165, 22, 216, 1))
        outgoingPackets.offer(IfOpenSubMessage(165, 23, 239, 1))
        outgoingPackets.offer(IfOpenSubMessage(165, 17, 7, 1))
        outgoingPackets.offer(IfOpenSubMessage(165, 10, 593, 1))
        outgoingPackets.offer(IfOpenSubMessage(165, 26, 160, 1))

        outgoingPackets.offer(UpdateDisplayWidgetsMessage())

        outgoingPackets.offer(RunClientScriptMessage(2014, 0, 0, 0, 0, 0, 0))

        outgoingPackets.offer(RunClientScriptMessage(2015, 0))

        outgoingPackets.offer(UnknownMessage(true))
        outgoingPackets.offer(UpdateRunEnergyMessage(energy))

        skillManager.skillState.onEach { it ->
            it.forEach {
                outgoingPackets.offer(
                    UpdateSkillMessage(
                        it.id,
                        it.level,
                        it.experience
                    )
                )
            }
        }.launchIn(CoroutineScope(Dispatchers.Default))

        skillManager.invalidateSkills()

        //containerManager().inventory()?.forceSync()
        val coords = WorldCoordinate.from30BitHash(details.coordinate)
        coordinate.set(coords.x, coords.z, coords.plane)
        pendingTeleport = coordinate

        this.add(actorAttributes.valueChangeListener.subscribe {
            if (it == Player::energy.name) {
                outgoingPackets.offer(UpdateRunEnergyMessage(energy))
            }
        })

        flowOf(*RSPluginManager.getExtensions<LoginActionFactory>().toTypedArray())
            .map { it.registerLoginAction(this) }
            .onEach { it.onLogin(this) }
            .launchIn(CoroutineScope(Dispatchers.Unconfined))
    }

    override fun dispose() {
        //TODO - logout code
        movement.clear()
        if (outgoingPackets.offer(LogoutFullMessage())) {
            disposable.dispose()
        }
    }

    override fun save() {
        val serv = get<JDBCPoolingService>()
        val con = containerManager
        con.inventory.let { details.inventory = it.toString() }
        con.equipment.let { details.equipment = it.toString() }
        details.skills = skillManager.toString()
        details.coordinate = coordinate.as30BitInteger
        details.attributes.putAll(actorAttributes.attributes)
        serv.withTransaction { s ->
            s.update(details)
            this.commit()
        }
    }

    @ExperimentalCoroutinesApi
    fun load() {
        val gson = get<Gson>()

        val inv: Inventory? = gson.fromJson(details.inventory, Inventory::class.java)
        val equ: Equipment? = gson.fromJson(details.equipment, Equipment::class.java)
        //val bak = gson.fromJson(details.bank, Bank::class.java)

        containerManager.registerContainer(93, inv ?: Inventory())
        containerManager.registerContainer(94, equ ?: Equipment())

        inventory().containerState.onEach {
            messages().sendItemContainerFull(149, 0, 93, it.container)
        }.launchIn(CoroutineScope(Dispatchers.Default))
        /*equipment().containerState.onEach {
            messages().sendItemContainerFull(387, 0, 94, it.container)
        }.launchIn(CoroutineScope(Dispatchers.Default))*/

        if (details.skills.isNotEmpty()) {
            val skills = gson.fromJson(details.skills, Array<Skill>::class.java)
            skills.forEach {
                skillManager.skills[it.id] = it
            }
        }


        actorAttributes.attributes.putAll(details.attributes)
    }

    override fun logout() {
        this.save()
        this.dispose()
    }

    override fun username(): String = details.username

    override fun displayName(): String = details.displayName

    override fun messages(): AbstractMessageHandler {
        return messageHandler
    }

    inline fun <reified M : IMessages> messagesFromType(): M {
        return messageHandler.ofType()
    }

}