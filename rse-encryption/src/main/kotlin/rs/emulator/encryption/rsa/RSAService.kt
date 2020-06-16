package rs.emulator.encryption.rsa

import com.google.common.util.concurrent.AbstractIdleService
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.io.pem.*
import java.io.IOException
import java.math.BigInteger
import java.nio.file.*
import java.security.*
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec

/**
 *
 * @author Chk
 */
class RSAService : AbstractIdleService()
{

    private lateinit var keyPath: Path

    private var radix = -1

    lateinit var exponent: BigInteger

    lateinit var modulus: BigInteger

    override fun startUp()
    {

        keyPath = Paths.get("./key.pem")

        radix = 16

        if (!Files.exists(keyPath))
            createPair(2048)

        try
        {

            PemReader(Files.newBufferedReader(keyPath)).use { reader ->

                val pem = reader.readPemObject()

                val keySpec = PKCS8EncodedKeySpec(pem.content)

                Security.addProvider(BouncyCastleProvider())

                val factory = KeyFactory.getInstance("RSA", "BC")

                val privateKey = factory.generatePrivate(keySpec) as RSAPrivateKey

                exponent = privateKey.privateExponent

                modulus = privateKey.modulus

            }

        }
        catch (exception: Exception)
        {
            throw ExceptionInInitializerError(IOException("Error parsing RSA key pair: ${keyPath.toAbsolutePath()}", exception))
        }

    }

    /**
     * Credits: Apollo
     *
     * @author Graham
     * @author Major
     * @author Cube
     */
    private fun createPair(bitCount: Int)
    {

        Security.addProvider(BouncyCastleProvider())

        val keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC")
        keyPairGenerator.initialize(bitCount)
        val keyPair = keyPairGenerator.generateKeyPair()

        val privateKey = keyPair.private as RSAPrivateKey
        val publicKey = keyPair.public as RSAPublicKey

        println("")
        println("Place these keys in the client (find BigInteger(\"10001\" in client code):")
        println("--------------------")
        println("public key: " + publicKey.publicExponent.toString(radix))
        println("modulus: " + publicKey.modulus.toString(radix))
        println("")

        try
        {
            PemWriter(Files.newBufferedWriter(keyPath)).use { writer -> writer.writeObject(PemObject("RSA PRIVATE KEY", privateKey.encoded)) }
        }
        catch (e: Exception)
        {
           // logger().error(e) { "Failed to write private key to ${keyPath.toAbsolutePath()}" }
        }

    }

    override fun shutDown()
    {

    }

}