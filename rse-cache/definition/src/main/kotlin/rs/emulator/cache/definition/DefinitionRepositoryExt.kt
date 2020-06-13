package rs.emulator.cache.definition

import org.koin.java.KoinJavaComponent.getKoin

/**
 *
 * @author Chk
 */
fun definition(): DefinitionRepository = getKoin().get()