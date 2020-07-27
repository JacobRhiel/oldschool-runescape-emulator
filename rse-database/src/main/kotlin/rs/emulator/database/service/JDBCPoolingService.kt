package rs.emulator.database.service

import com.google.common.util.concurrent.AbstractIdleService
import io.github.classgraph.ClassGraph
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.Transaction
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
class JDBCPoolingService : AbstractIdleService() {

    lateinit var factory: SessionFactory

    inline fun <reified T> withTransaction(crossinline block: Transaction.(Session) -> T): T {
        return Observable.just(factory)
            .observeOn(Schedulers.io())
            .map { it.openSession() }
            .map { it to it.beginTransaction() }
            .switchMap { p ->
                Observable.just(block(p.second, p.first))
                    .doOnError { p.second.rollback() }
                    .doFinally { p.first.close() }
            }.singleElement().blockingGet()
    }

    override fun startUp() {

        val properties = Properties()

        properties["hibernate.hikari.dataSource.user"] = "javatar"
        properties["hibernate.hikari.dataSource.password"] = "david@aka@javatar"
        properties["hibernate.hikari.dataSource.databaseName"] = "runescape-emulator"
        properties["hibernate.hikari.dataSource.portNumber"] = "5432"
        properties["hibernate.hikari.dataSource.serverName"] = "140.82.2.221"
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

        val serviceRegistry: ServiceRegistry =
            StandardServiceRegistryBuilder().applySettings(configuration.properties).build()

        try {

            factory = configuration
                .buildSessionFactory(
                    serviceRegistry
                )
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun shutDown() {

        factory.close()

    }

}