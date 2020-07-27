package rs.emulator.service.login.worker

import rs.emulator.database.service.JDBCPoolingService
import rs.emulator.entity.details.PlayerDetails
import rs.emulator.entity.details.Privilege
import rs.emulator.service.login.LoginStatus
import rs.emulator.service.login.network.message.LoginRequestMessage
import rs.emulator.utilities.koin.get

/**
 *
 * @author Chk
 */
class LoginWorker(val request: LoginRequestMessage) : Runnable {

    private var result: LoginStatus = LoginStatus.ACCEPTED
    private var details: PlayerDetails = PlayerDetails(
        request.credentials.username,
        request.credentials.username,
        request.credentials.password,
        Privilege(
            0, "player",
            canDrop = true,
            canTalk = true,
            canTrade = true,
            canTeleport = false,
            isSystemAdmin = false
        ),
        "",
        "",
        "",
        "",
        false,
        false
    )

    fun execute(): Pair<LoginStatus, PlayerDetails> {

        run()

        return result to details
    }

    override fun run() {
        val service = get<JDBCPoolingService>()
        val username = request.credentials.username
        val password = request.credentials.password
        this.details = service.withTransaction { tx ->
            (tx.get(PlayerDetails::class.java, username) ?: details.also { tx.save(it); this.commit() })
        }.apply {
            result = when {
                this.password != password -> LoginStatus.INVALID_CREDENTIALS
                //TODO - banned
                else -> LoginStatus.ACCEPTED
            }
        }
    }

}