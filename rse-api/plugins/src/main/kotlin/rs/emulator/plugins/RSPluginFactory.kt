package rs.emulator.plugins

import org.pf4j.Plugin
import org.pf4j.PluginFactory
import org.pf4j.PluginWrapper
import java.lang.reflect.Modifier

/**
 *
 * @author javatar
 */

internal class RSPluginFactory : PluginFactory {
    override fun create(pluginWrapper: PluginWrapper?): Plugin? {
        val pluginClassName = pluginWrapper!!.descriptor.pluginClass
        val pluginClass = try {
            pluginWrapper.pluginClassLoader.loadClass(pluginClassName)
        } catch (e: ClassNotFoundException) {
            return null
        }
        val modifiers = pluginClass.modifiers
        if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)
            || !Plugin::class.java.isAssignableFrom(pluginClass)
        ) {
            return null
        }
        return try {
            (pluginClass.getConstructor(PluginWrapper::class.java).newInstance(pluginWrapper) as Plugin)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}