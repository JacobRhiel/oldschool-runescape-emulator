package rs.emulator.utilities.logger

import mu.KLogger
import mu.KotlinLogging

/**
 * An extension for holding logging functions directly from 'Any' class.
 * @author Chk
 */

/**
 * Default logger instance.
 */
fun Any.logger(): KLogger = KotlinLogging.logger(javaClass.simpleName)

/**
 * Information logging extension function.
 */
fun Any.info(msg: String) = logger().info(msg)

fun Any.info(msg: String, vararg args: Any) = logger().info(msg, args)

/**
 * Warning logging extension function.
 */
fun Any.warn(msg: String) = logger().warn(msg)

/**
 * Debug logging extension function.
 */
fun Any.debug(msg: String) = logger().debug(msg)

fun Any.debug(msg: String, vararg args: Any) = logger().debug(msg, args)

/**
 * Error logging extension function.
 */
fun Any.error(msg: String) = logger().error(msg)

fun Any.error(msg: String, vararg args: Any) = logger().error(msg, args)

/**
 * Trace logging extension function.
 */
fun Any.trace(msg: String) = logger().trace(msg)