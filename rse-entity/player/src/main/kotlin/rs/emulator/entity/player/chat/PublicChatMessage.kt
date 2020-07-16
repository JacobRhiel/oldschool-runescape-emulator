package rs.emulator.entity.player.chat

/**
 *
 * @author Chk
 */
data class PublicChatMessage(
    val text: String,
    val icon: Int,
    val type: Int,
    val effect: Int,
    val color: Int
)
