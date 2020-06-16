package rs.emulator.database.annotations

/**
 * Annotates a class that requires a no-args constructor created for JPA.
 * This is specific [kotlin-maven-noarg] dependency in parent pom.
 * @author Chk
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class NoArg