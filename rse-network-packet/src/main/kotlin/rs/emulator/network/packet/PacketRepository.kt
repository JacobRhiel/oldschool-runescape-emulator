package rs.emulator.network.packet

import com.google.gson.Gson
import io.github.classgraph.ClassGraph
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import java.lang.reflect.Modifier

/**
 *
 * @author Chk
 */
class PacketRepository(private val encoders: ObjectArrayList<PacketMetaData>,
                       private val decoders: ObjectArrayList<PacketMetaData>
)
{

    fun fetchDecoder(opcode: Int) = decoders.firstOrNull { it.opcode == opcode }

    fun fetchEncoder(opcode: Int) = encoders.firstOrNull { it.opcode == opcode }

    companion object
    {

        internal fun saveJson()
        {

            val gson = Gson().newBuilder().setPrettyPrinting().create()

            println(gson.toJson(this))

        }

        internal fun parseJson() : PacketRepository
        {

            ClassGraph().acceptClasspathElementsContainingResourcePath().acceptPackages().scan().use { result ->

                val json = result.getResourcesWithLeafName("packet-data.json")

                val builder = Gson().newBuilder()
                    .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                    .create()

                return builder.fromJson(
                    json.first().contentAsString,
                    PacketRepository::class.java
                )

            }

        }

    }

}