package rs.emulator.entity.player.update.sync

import rs.emulator.entity.player.update.flag.PlayerUpdateFlag

/**
 *
 * @author Chk
 */
class SyncInformation
{

    private var maskFlag: Int = 0

    private var skipFlag: Int = 0

    fun setFlag(flag: Int) = apply { this.skipFlag = (this.skipFlag) or flag }

    fun addMaskFlag(flag: Int) = apply { this.skipFlag = this.skipFlag or flag }

    fun addMaskFlag(updateFlag: PlayerUpdateFlag) = apply { this.maskFlag = this.maskFlag or updateFlag.bit }

    fun removeMaskFlag(updateFlag: PlayerUpdateFlag) = apply { this.maskFlag = this.maskFlag and updateFlag.bit.inv() }

    fun hasFlag(flag: Int) = (this.skipFlag and flag) == flag

    fun hasMaskFlag(flag: PlayerUpdateFlag) = (this.maskFlag and flag.bit) == flag.bit

    fun noFlag(flag: Int) = (this.skipFlag and flag) == 0

    fun fetchFlag() = this.skipFlag

    fun fetchMaskFlag() = this.maskFlag

    fun requiresUpdate() = this.maskFlag != 0

    fun resetFlag() = apply{ this.skipFlag = 0 }

}