package rs.emulator.database.annotation

import kotlin.reflect.KClass

/**
 *
 * @author Chk
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class DatabaseMetaData(
    vararg val tableTypes: KClass<*>
)