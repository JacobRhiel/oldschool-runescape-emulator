package rs.emulator.entity.player.update.sync

import rs.emulator.entity.player.update.flag.PlayerUpdateFlag

/**
 *
 * @author Chk
 */
class SyncInformation
{

    private var flag: Int = 0

    fun setFlag(flag: Int) = apply { this.flag = (this.flag) or flag }

    fun addFlag(flag: Int) = apply { this.flag = this.flag or flag }

    fun addFlag(updateFlag: PlayerUpdateFlag) = apply { this.flag = this.flag or updateFlag.bit }

    fun hasFlag(flag: Int) = (this.flag and flag) != 0

    fun noFlag(flag: Int) = (this.flag and flag) == 0

    fun fetchFlag() = this.flag

    fun requiresUpdate() = flag != 0

    fun resetFlag() = apply{ this.flag = 0 }

}