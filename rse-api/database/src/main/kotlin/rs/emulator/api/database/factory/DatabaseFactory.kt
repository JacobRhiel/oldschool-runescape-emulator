package rs.emulator.api.database.factory

import org.hibernate.HibernateException
import org.hibernate.SessionFactory
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder
import org.hibernate.boot.registry.StandardServiceRegistry
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Configuration
import rs.emulator.api.database.annotation.DatabaseMetaData
import rs.emulator.api.database.properties.DatabaseProperties
import javax.persistence.Persistence
import kotlin.reflect.full.findAnnotation

/**
 *
 * @author Chk
 */
object DatabaseFactory
{

    private val mappedTypes = mutableMapOf<Class<*>, IDatabase>()

    fun provide(type: Class<*>, block : IDatabase.() -> Unit = {}) : IDatabase
        = mappedTypes[type] ?: throw Error("Database does not exist for type: $type")

    fun create(clazz: Any, propertiesBuilder: DatabaseProperties.Builder.() -> Unit): Database
    {

        val builder = DatabaseProperties.Builder()

        propertiesBuilder(builder)

        val properties = builder.build()

        val configuration = Configuration().configure().addProperties(properties.toProperties())

        val metaData = clazz::class.findAnnotation<DatabaseMetaData>()
            ?: throw Error("Unable to create database with no table types.")

        metaData.tableTypes.forEach {
            configuration.addAnnotatedClass(it.java)
        }

        val factory: SessionFactory

        val database: Database

        try
        {

            factory = configuration.buildSessionFactory(initServiceFactory(configuration, properties.classLoader))

            database = Database(metaData, factory)

            metaData.tableTypes.forEach { mappedTypes[it::class.java] = database }

        }
        catch(e: HibernateException)
        {

            e.printStackTrace()

            throw Error("DatabaseFactory configuration not suitable to construct SessionFactory.")

        }

        println("Created database: $database")

        return database

    }

    private fun initServiceFactory(configuration: Configuration, classLoader: ClassLoader): StandardServiceRegistry =
        StandardServiceRegistryBuilder(
            BootstrapServiceRegistryBuilder()
                .applyClassLoader(classLoader)
                .build()
        )
            .applySettings(configuration.properties)
            .build()

}