package rs.emulator.database.service

import com.google.common.util.concurrent.AbstractIdleService
import io.github.classgraph.ClassGraph
import org.hibernate.SessionFactory
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Configuration
import org.hibernate.service.ServiceRegistry
import rs.emulator.database.entry.Entry
import rs.emulator.utilities.logger.info
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
        properties["hibernate.hikari.dataSource.portNumber"] = "5432"
        properties["hibernate.hikari.dataSource.serverName"] = "localhost"
        properties["connection.provider_class"] = "org.hibernate.hikaricp.internal.HikariCPConnectionProvider"

        val configuration = Configuration().configure().addProperties(properties)

        ClassGraph().enableAllInfo().acceptPackages().scan().use {

            val classes = it.getClassesImplementing(Entry::class.qualifiedName)
                .filter { clazz -> !clazz.isInterface && !clazz.isAbstract && !clazz.isAnnotation && !clazz.isEnum }
                .loadClasses()
                .filterNotNull()

            classes.forEach { clazz -> configuration.addAnnotatedClass(clazz) }

            println(classes.toTypedArray().contentDeepToString())

            info("Loaded ${classes.size} data entry classes.")

        }

        val serviceRegistry: ServiceRegistry = StandardServiceRegistryBuilder().applySettings(configuration.properties).build()

        factory = configuration.buildSessionFactory(serviceRegistry)

    }

    override fun shutDown()
    {

    }

}