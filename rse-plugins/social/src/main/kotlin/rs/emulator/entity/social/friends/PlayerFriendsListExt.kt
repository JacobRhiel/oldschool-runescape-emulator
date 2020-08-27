package rs.emulator.entity.social.friends

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author Chk
 */
private val friends = mutableListOf<IPlayer>()

private val ignore = mutableListOf<IPlayer>()

@ExperimentalCoroutinesApi
val IPlayer.friendsList: MutableList<IPlayer>
    get() = friends

@ExperimentalCoroutinesApi
val IPlayer.ignoreList: MutableList<IPlayer>
    get() = ignore