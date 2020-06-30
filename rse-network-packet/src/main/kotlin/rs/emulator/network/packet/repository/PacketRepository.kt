package rs.emulator.network.packet.repository

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.DecodingGamePacketMessage
import rs.emulator.network.packet.message.EncodingGamePacketMessage
import rs.emulator.packet.api.ActionType
import rs.emulator.packet.api.PacketType
import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.IGamePacketListener
import rs.emulator.packet.api.IGamePacketMessage
import rs.emulator.packet.api.IPacketDecoder
import rs.emulator.packet.api.IPacketEncoder
import kotlin.reflect.KClass

/**
 *
 * @author Chk
 */
class PacketRepository(private val encoders: HashMap<KClass<out GamePacketMessage>, EncodingGamePacketMessage> = hashMapOf(),
                       private val decoders: HashMap<KClass<out GamePacketMessage>, HashMap<Int, DecodingGamePacketMessage>> = hashMapOf()
)
{

    fun fetchDecoder(opcode: Int): DecodingGamePacketMessage
    {

        var decoder: DecodingGamePacketMessage?

        decoders.values.forEach {

            decoder = it.values.firstOrNull { decoder -> decoder.opcode == opcode }

            if(decoder != null)
                return decoder!!

        }

        throw Error("Unknown packet decoder for opcode: $opcode")

    }

    fun <T : GamePacketMessage> fetchEncoder(clazz: KClass<T>) = encoders[clazz] ?: throw Error("Unknown encoder")

    fun <T : GamePacketMessage> putEncoder(opcode: Int, encoder: IPacketEncoder<out IGamePacketMessage, out IPlayer>, actionType: ActionType = ActionType.NONE, packetType: PacketType = PacketType.FIXED, clazz: KClass<out T>) =
        encoders.computeIfAbsent(clazz) { EncodingGamePacketMessage(opcode, encoder, packetType = packetType, actionType = actionType) }

    fun <T : GamePacketMessage> putDecoder(opcode: Int, decoder: IPacketDecoder<out IGamePacketMessage, Player>, listener: IGamePacketListener<out IGamePacketMessage, Player>, packetType: PacketType = PacketType.FIXED, actionType: ActionType = ActionType.NONE, length: Int = 0, ignore: Boolean = false, clazz: KClass<out T>)
        = putDecoders(opcodes = *intArrayOf(opcode), decoder = decoder, listener = listener, packetType = packetType, actionType = actionType, length = length, ignore = ignore, clazz = clazz)

    fun <T : GamePacketMessage> putDecoders(vararg opcodes: Int, decoder: IPacketDecoder<out IGamePacketMessage, Player>, listener: IGamePacketListener<out IGamePacketMessage, Player>, packetType: PacketType = PacketType.FIXED, actionType: ActionType = ActionType.NONE, length: Int = 0, ignore: Boolean = false, clazz: KClass<out T>)
    {

        decoders.computeIfAbsent(clazz) {

            val decoderMap = hashMapOf<Int, DecodingGamePacketMessage>()

            opcodes.forEach { opcode -> decoderMap.computeIfAbsent(opcode) { DecodingGamePacketMessage(opcode, decoder, listener, packetType, actionType, length, ignore) } }

            decoderMap

        }


    }

}