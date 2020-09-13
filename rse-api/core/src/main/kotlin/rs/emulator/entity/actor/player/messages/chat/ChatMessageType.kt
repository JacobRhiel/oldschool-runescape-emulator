package rs.emulator.entity.actor.player.messages.chat

/**
 *
 * @author javatar
 */

enum class ChatMessageType(val type : Int) {

    /**
     * A normal game message.
     */
    GAMEMESSAGE(0),
    /**
     * A message in the public chat from a moderator
     */
    MODCHAT(1),
    /**
     * A message in the public chat.
     */
    PUBLICCHAT(2),
    /**
     * A private message from another player.
     */
    PRIVATECHAT(3),
    /**
     * A message that the game engine sends.
     */
    ENGINE(4),
    /**
     * A message received when a friend logs in or out.
     */
    LOGINLOGOUTNOTIFICATION(5),
    /**
     * A private message sent to another player.
     */
    PRIVATECHATOUT(6),
    /**
     * A private message received from a moderator.
     */
    MODPRIVATECHAT(7),
    /**
     * A message received in friends chat.
     */
    FRIENDSCHAT(9),
    /**
     * A message received with information about the current friends chat.
     */
    FRIENDSCHATNOTIFICATION(11),
    /**
     * A trade request being sent.
     */
    TRADE_SENT(12),
    /**
     * A game broadcast.
     */
    BROADCAST(14),
    /**
     * An abuse report submitted.
     */
    SNAPSHOTFEEDBACK(26),
    /**
     * Examine item description.
     */
    ITEM_EXAMINE(27),
    /**
     * Examine NPC description.
     */
    NPC_EXAMINE(28),
    /**
     * Examine object description.
     */
    OBJECT_EXAMINE(29),
    /**
     * Adding player to friend list.
     */
    FRIENDNOTIFICATION(30),
    /**
     * Adding player to ignore list.
     */
    IGNORENOTIFICATION(31),
    /**
     * An autotyper message from a player.
     */
    AUTOTYPER(90),
    /**
     * An autotyper message from a mod.
     */
    MODAUTOTYPER(91),
    /**
     * A game message (ie. when a setting is changed).
     */
    CONSOLE(99),
    /**
     * A message received when somebody sends a trade offer.
     */
    TRADEREQ(101),
    /**
     * A message received when completing a trade or a duel
     */
    TRADE(102),
    /**
     * A message received when somebody sends a duel offer.
     */
    CHALREQ_TRADE(103),
    /**
     * A message received when someone sends a friends chat challenge offer.
     */
    CHALREQ_FRIENDSCHAT(104),
    /**
     * A message that was filtered.
     */
    SPAM(105),
    /**
     * A message that is relating to the player.
     */
    PLAYERRELATED(106),
    /**
     * A message that times out after 10 seconds.
     */
    TENSECTIMEOUT(107),
    /**
     * The "Welcome to RuneScape" message
     */
    WELCOME(108),
    /**
     * An unknown message type.
     */
    UNKNOWN(-1);

    companion object {

        fun fromType(type : Int) : ChatMessageType = values().first { it.type == type }

    }

}