package rs.emulator.entity.player

import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.*
import rs.dusk.engine.path.Finder
import rs.emulator.database.service.JDBCPoolingService
import rs.emulator.entity.actor.Actor
import rs.emulator.entity.actor.affects.AffectHandler
import rs.emulator.entity.actor.attributes.ActorAttributes
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.messages.AbstractMessageHandler
import rs.emulator.entity.actor.player.messages.IMessages
import rs.emulator.entity.actor.player.widgets.WidgetViewport
import rs.emulator.entity.details.PlayerDetails
import rs.emulator.entity.material.containers.ItemContainerManager
import rs.emulator.entity.material.containers.equipment
import rs.emulator.entity.material.containers.impl.Equipment
import rs.emulator.entity.material.containers.impl.Inventory
import rs.emulator.entity.material.containers.inventory
import rs.emulator.entity.player.chat.PublicChatText
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.entity.player.update.sync.SyncInformation
import rs.emulator.entity.player.viewport.Viewport
import rs.emulator.entity.skills.Skill
import rs.emulator.entity.skills.SkillManager
import rs.emulator.network.packet.message.IncomingPacket
import rs.emulator.network.packet.message.outgoing.LogoutFullMessage
import rs.emulator.network.packet.message.outgoing.UpdateRunEnergyMessage
import rs.emulator.packet.api.IPacketMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.LoginActionFactory
import rs.emulator.plugins.extensions.factories.LogoutActionFactory
import rs.emulator.region.WorldCoordinate
import rs.emulator.region.coordinate.Coordinate
import rs.emulator.regions.zones.RegionZone
import rs.emulator.regions.zones.events.MessageBroadcastZoneEvent
import rs.emulator.utilities.contexts.scopes.ActorScope
import rs.emulator.utilities.koin.get
import rs.emulator.world.World
import rs.emulator.world.regions.RegionZoneManager
import java.util.concurrent.atomic.AtomicLong

@ExperimentalCoroutinesApi
class Player(
    index: Int,
    val outgoingPackets: BroadcastChannel<IPacketMessage>,
    val incomingPackets: ReceiveChannel<IncomingPacket>,
    private val disposable: CompositeDisposable,
    override val details: PlayerDetails
) : Actor(index), IPlayer, Disposable by disposable, DisposableContainer by disposable {

    val world: World = get()

    val viewport = Viewport(this)

    val syncInfo = SyncInformation().apply { this.addMaskFlag(PlayerUpdateFlag.APPEARANCE) }

    val messageHandler = MessageHandler(this)

    val idleMouseTicks = AtomicLong(0L)

    var pendingTeleport: Coordinate? = null

    var pendingPublicChatMessage: PublicChatText? = null

    override val searchPattern: Finder = pathFinder.bfs

    override val playerIndex get() = super.index

    override val currentZone: MutableStateFlow<RegionZone> = MutableStateFlow(RegionZone.WORLD)

    @ExperimentalCoroutinesApi
    override val skillManager: SkillManager = SkillManager()

    override val widgetViewport = WidgetViewport(this)

    override val actorAttributes: ActorAttributes = ActorAttributes()

    override val containerManager: ItemContainerManager = ItemContainerManager()

    override val affectHandler: AffectHandler<IPlayer> = AffectHandler()

    override var energy: Int by actorAttributes.Int(100).markPersistent().apply {
        add(this.changeListener.subscribe {
            outgoingPackets.sendBlocking(UpdateRunEnergyMessage(it))
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
        flowOf(*RSPluginManager.getExtensions<LoginActionFactory>().toTypedArray())
            .map { it.registerLoginAction(this) }
            .onEach { it.onLogin(this) }
            .launchIn(get<ActorScope>())
    }

    override fun dispose() {
        //TODO - logout code
        movement.clear()
        outgoingPackets.sendBlocking(LogoutFullMessage())
        disposable.dispose()
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
        /*flowOf(*RSPluginManager.getExtensions<SavePlayerFactory>().toTypedArray())
            .map { it.registerSaveAction(this) }
            .onEach { it.onSave(this) }
            .launchIn(CoroutineScope(Dispatchers.IO))*/
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
        equipment().containerState.onEach {
            messages().sendItemContainerFull(-1, -1, 94, it.container)
            syncInfo.addMaskFlag(PlayerUpdateFlag.APPEARANCE)
        }.launchIn(CoroutineScope(Dispatchers.Default))

        if (details.skills.isNotEmpty()) {
            val skills = gson.fromJson(details.skills, Array<Skill>::class.java)
            skills.forEach {
                skillManager.skills[it.id] = it
            }
        }
        actorAttributes.attributes.putAll(details.attributes)
        val coord = WorldCoordinate.from30BitHash(details.coordinate)
        coordinate.set(coord.x, coord.y, coord.plane)

        /*flowOf(*RSPluginManager.getExtensions<SavePlayerFactory>().toTypedArray())
            .map { it.registerSaveAction(this) }
            .onEach { it.onLoad(this) }
            .launchIn(CoroutineScope(Dispatchers.IO))*/
    }

    override fun logout() {
        this.save()
        this.dispose()
        flowOf(*RSPluginManager.getExtensions<LogoutActionFactory>().toTypedArray())
            .map { it.registerLogoutAction(this) }
            .onEach { it.onLogout(this) }
            .launchIn(get<ActorScope>())
    }

    override fun username(): String = details.username

    override fun displayName(): String = details.displayName

    override fun messages(): AbstractMessageHandler {
        return messageHandler
    }

    override fun setTeleportCoordinate(coordinate: Coordinate) {
        pendingTeleport = coordinate
    }

    inline fun <reified M : IMessages> messagesFromType(): M {
        return messageHandler.ofType()
    }

}