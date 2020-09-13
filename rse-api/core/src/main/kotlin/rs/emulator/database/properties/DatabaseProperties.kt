package rs.emulator.database.properties

import org.hibernate.boot.registry.classloading.internal.ClassLoaderServiceImpl
import org.hibernate.cfg.AvailableSettings
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider
import org.hibernate.hikaricp.internal.HikariCPConnectionProvider
import java.net.InetSocketAddress
import java.util.*
import javax.persistence.Persistence

/**
 *
 * @author Chk
 */
data class DatabaseProperties(
    val username: String,
    val password: String,
    val databaseName: String,
    val address: InetSocketAddress,
    val provider: ConnectionProvider = HikariCPConnectionProvider(),
    val classLoader: ClassLoader
)
{

    constructor(username: String,
                password: String,
                databaseName: String,
                address: String,
                provider: ConnectionProvider = HikariCPConnectionProvider(),
                classLoader: ClassLoader
    ) : this(username, password, databaseName, InetSocketAddress(address, 5432), provider, classLoader)

    fun toProperties() : Properties
    {

        val properties = Properties()

        properties["hibernate.hikari.dataSource.user"] = username
        properties["hibernate.hikari.dataSource.password"] = password
        properties["hibernate.hikari.dataSource.databaseName"] = databaseName
        properties["hibernate.hikari.dataSource.portNumber"] = address.port.toString()
        properties["hibernate.hikari.dataSource.serverName"] = address.hostName
        properties["connection.provider_class"] = provider::class.java.name

        return properties

    }

    class Builder()
    {

        var username: String = "default"

        var password: String = ""

        var databaseName: String = "default"

        var port: Int = 5432

        var address: InetSocketAddress = InetSocketAddress("localhost", port)

        var provider: ConnectionProvider = HikariCPConnectionProvider()

        lateinit var classLoader: ClassLoader

        fun username(username: String) = this.apply { this.username = username }

        fun password(password: String) = this.apply { this.password = password }

        fun databaseName(databaseName: String) = this.apply { this.databaseName = databaseName }

        fun address(address: String, port: Int) = this.apply { this.address = InetSocketAddress(address, port) }

        fun address(address: String) = address(address, port)

        fun address(address: InetSocketAddress) = this.apply { this.address = address }

        fun provider(provider: ConnectionProvider) = this.apply { this.provider = provider }

        fun classLoader(classLoader: ClassLoader) = this.apply { this.classLoader = classLoader }

        fun build(): DatabaseProperties = DatabaseProperties(username, password, databaseName, address, provider, classLoader)

    }

}