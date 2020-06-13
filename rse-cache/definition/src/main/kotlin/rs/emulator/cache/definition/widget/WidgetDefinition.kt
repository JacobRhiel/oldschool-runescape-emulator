package rs.emulator.cache.definition.widget

import rs.emulator.cache.definition.Definition
import rs.emulator.cache.definition.widget.script.ClientScript1Instruction

/**
 *
 * @author Chk
 */
class WidgetDefinition(identifier: Int) : Definition(identifier)
{

    var idd = -1
    var isIf3 = false
    var type = 0
    var contentType = 0
    var originalX = 0
    var originalY = 0
    var originalWidth = 0
    var originalHeight = 0
    var widthMode = 0
    var heightMode = 0
    var xPositionMode = 0
    var yPositionMode = 0
    var parentId = -1
    var isHidden = false
    var scrollWidth = 0
    var scrollHeight = 0
    var noClickThrough = false
    var spriteId = -1
    var textureId = 0
    var spriteTiling = false
    var opacity = 0
    var borderType = 0
    var shadowColor = 0
    var flippedVertically = false
    var flippedHorizontally = false
    var modelType = 1
    var modelId = -1
    var offsetX2d = 0
    var offsetY2d = 0
    var rotationX = 0
    var rotationY = 0
    var rotationZ = 0
    var modelZoom = 100
    var animation = -1
    var orthogonal = false
    var modelHeightOverride = 0
    var fontId = -1
    var text = ""
    var lineHeight = 0
    var xTextAlignment = 0
    var yTextAlignment = 0
    var textShadowed = false
    var textColor = 0
    var filled = false
    var lineWidth = 1
    var lineDirection = false
    var clickMask = 0
    var name = ""
    lateinit var actions: Array<String?>
    var dragDeadZone = 0
    var dragDeadTime = 0
    var dragRenderBehavior = false
    var targetVerb = ""
    lateinit var onLoadListener: Array<Any>
    lateinit var onMouseOverListener: Array<Any>
    lateinit var onMouseLeaveListener: Array<Any>
    lateinit var onTargetLeaveListener: Array<Any>
    lateinit var onTargetEnterListener: Array<Any>
    lateinit var onVarTransmitListener: Array<Any>
    lateinit var onInvTransmitListener: Array<Any>
    lateinit var onStatTransmitListener: Array<Any>
    lateinit var onTimerListener: Array<Any>
    lateinit var onOpListener: Array<Any>
    lateinit var onMouseRepeatListener: Array<Any>
    lateinit var onClickListener: Array<Any>
    lateinit var onClickRepeatListener: Array<Any>
    lateinit var onReleaseListener: Array<Any>
    lateinit var onHoldListener: Array<Any>
    lateinit var onDragListener: Array<Any>
    lateinit var onDragCompleteListener: Array<Any>
    lateinit var onScrollWheelListener: Array<Any>
    lateinit var varTransmitTriggers: IntArray
    lateinit var invTransmitTriggers: IntArray
    lateinit var statTransmitTriggers: IntArray
    var hasListener = false

    var menuType = 0

    // This is set to a siblings' child id when that widget should get a hover effect when this one is hovered
    var hoveredSiblingId = 0
    lateinit var alternateOperators: IntArray
    lateinit var alternateRhs: IntArray
    lateinit var clientScripts: Array<Array<ClientScript1Instruction>?>
    lateinit var itemIds: IntArray
    lateinit var itemQuantities: IntArray
    var xPitch = 0
    var yPitch = 0
    lateinit var xOffsets: IntArray
    lateinit var yOffsets: IntArray
    lateinit var sprites: IntArray
    lateinit var configActions: Array<String?>
    var alternateText = ""
    var alternateTextColor = 0
    var hoveredTextColor = 0
    var alternateHoveredTextColor = 0
    var alternateSpriteId = -1
    var alternateModelId = -1
    var alternateAnimation = -1
    var spellName = ""
    var tooltip = "Ok"

}