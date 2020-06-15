package rs.emulator.cache.definition.entity.spotanim

import rs.emulator.cache.definition.Definition

/**
 *
 * @author Chk
 */
data class SpotAnimDefinition(val identifier: Int,
                              var rotation: Int = 0,
                              var textureToReplace: ShortArray = shortArrayOf(),
                              var textureToFind: ShortArray = shortArrayOf(),
                              var resizeY: Int = 128,
                              var animationId: Int = -1,
                              var recolorToFind: ShortArray = shortArrayOf(),
                              var recolorToReplace: ShortArray = shortArrayOf(),
                              var resizeX: Int = 128,
                              var modelId: Int = 0,
                              var ambient: Int = 0,
                              var contrast: Int = 0
) : Definition(identifier)
{

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SpotAnimDefinition

        if (identifier != other.identifier) return false
        if (rotation != other.rotation) return false
        if (!textureToReplace.contentEquals(other.textureToReplace)) return false
        if (!textureToFind.contentEquals(other.textureToFind)) return false
        if (resizeY != other.resizeY) return false
        if (animationId != other.animationId) return false
        if (!recolorToFind.contentEquals(other.recolorToFind)) return false
        if (!recolorToReplace.contentEquals(other.recolorToReplace)) return false
        if (resizeX != other.resizeX) return false
        if (modelId != other.modelId) return false
        if (ambient != other.ambient) return false
        if (contrast != other.contrast) return false

        return true
    }

    override fun hashCode(): Int
    {
        var result = identifier
        result = 31 * result + rotation
        result = 31 * result + textureToReplace.contentHashCode()
        result = 31 * result + textureToFind.contentHashCode()
        result = 31 * result + resizeY
        result = 31 * result + animationId
        result = 31 * result + recolorToFind.contentHashCode()
        result = 31 * result + recolorToReplace.contentHashCode()
        result = 31 * result + resizeX
        result = 31 * result + modelId
        result = 31 * result + ambient
        result = 31 * result + contrast
        return result
    }

}
