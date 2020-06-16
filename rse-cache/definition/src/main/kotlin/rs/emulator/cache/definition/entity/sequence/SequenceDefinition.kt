package rs.emulator.cache.definition.entity.sequence

import rs.emulator.cache.definition.Definition

/**
 *
 * @author Chk
 */
data class SequenceDefinition(val identifier: Int,
                              var frameIds: IntArray = intArrayOf(),
                              var chatFrameIds: IntArray = intArrayOf(),
                              var frameLengths: IntArray = intArrayOf(),
                              var frameSounds: IntArray = intArrayOf(),
                              var frameStep: Int = -1,
                              var interLeaveLeave: IntArray = intArrayOf(),
                              var stretches: Boolean = false,
                              var forcedPriority: Int = 5,
                              var leftHandItem: Int = -1,
                              var rightHandItem: Int = -1,
                              var maxLoops: Int = 99,
                              var precedenceAnimating: Int = -1,
                              var priority: Int = -1,
                              var replyMode: Int = 2

) : Definition(identifier)
{

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SequenceDefinition

        if (identifier != other.identifier) return false
        if (!frameIds.contentEquals(other.frameIds)) return false
        if (!chatFrameIds.contentEquals(other.chatFrameIds)) return false
        if (!frameLengths.contentEquals(other.frameLengths)) return false
        if (!frameSounds.contentEquals(other.frameSounds)) return false
        if (frameStep != other.frameStep) return false
        if (!interLeaveLeave.contentEquals(other.interLeaveLeave)) return false
        if (stretches != other.stretches) return false
        if (forcedPriority != other.forcedPriority) return false
        if (leftHandItem != other.leftHandItem) return false
        if (rightHandItem != other.rightHandItem) return false
        if (maxLoops != other.maxLoops) return false
        if (precedenceAnimating != other.precedenceAnimating) return false
        if (priority != other.priority) return false
        if (replyMode != other.replyMode) return false

        return true
    }

    override fun hashCode(): Int
    {
        var result = identifier
        result = 31 * result + frameIds.contentHashCode()
        result = 31 * result + chatFrameIds.contentHashCode()
        result = 31 * result + frameLengths.contentHashCode()
        result = 31 * result + frameSounds.contentHashCode()
        result = 31 * result + frameStep
        result = 31 * result + interLeaveLeave.contentHashCode()
        result = 31 * result + stretches.hashCode()
        result = 31 * result + forcedPriority
        result = 31 * result + leftHandItem
        result = 31 * result + rightHandItem
        result = 31 * result + maxLoops
        result = 31 * result + precedenceAnimating
        result = 31 * result + priority
        result = 31 * result + replyMode
        return result
    }

}