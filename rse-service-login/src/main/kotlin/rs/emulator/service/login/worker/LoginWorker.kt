package rs.emulator.service.login.worker

import rs.emulator.service.login.LoginStatus
import rs.emulator.service.login.network.message.LoginRequestMessage

/**
 *
 * @author Chk
 */
class LoginWorker(val request: LoginRequestMessage) : Runnable
{

    private var result: LoginStatus = LoginStatus.ACCEPTED

    fun execute(): LoginStatus
    {

        run()

        return result

    }

    override fun run()
    {

/*        val player = Player(username = request.credentials.username, password = request.credentials.password)

        //load player
        player.get()

        //if(player.status.equals("banned", true))
           // result = LoginStatus.BANNED

        println(player.status)*/

        result = LoginStatus.ACCEPTED

        println("player loaded: FINISH THIS!")// + player.username)

    }

}