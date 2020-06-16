package rs.emulator.database.service

import com.google.common.util.concurrent.AbstractIdleService
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import java.util.*

/**
 *
 * @author Chk
 */
class JDBCPoolingService : AbstractIdleService()
{

    lateinit var factory: SessionFactory

    override fun startUp()
    {

        val properties = Properties()

        properties["hibernate.hikari.dataSource.user"] = "postgres"
        properties["hibernate.hikari.dataSource.password"] = "deadmau5"
        properties["hibernate.hikari.dataSource.databaseName"] = "runescape-emulator"
        properties["hibernate.hikari.dataSource.portNumber"] = 5432
        properties["hibernate.hikari.dataSource.serverName"] = "localhost"
        properties["connection.provider_class"] = "connection.provider_class"

        val configuration = Configuration().configure().addProperties(properties)

        factory = configuration.buildSessionFactory()

    }

    override fun shutDown()
    {

    }

}