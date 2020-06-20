package rs.emulator.service.login

/**
 *
 * @author Chk
 */
data class LoginCredentials(var username: String = "",
                            var password: String = "",
                            var authCode: Int = -1
)