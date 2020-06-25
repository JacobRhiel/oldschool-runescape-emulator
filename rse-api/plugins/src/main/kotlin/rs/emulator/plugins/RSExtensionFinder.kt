package rs.emulator.plugins

import org.pf4j.*
import java.util.*

/**
 *
 * @author javatar
 */

class RSExtensionFinder(private val manager : PluginManager, private val finder : ExtensionFinder = DefaultExtensionFinder(manager)) : ExtensionFinder by finder {

    override fun find(pluginId: String?): MutableList<ExtensionWrapper<Any>> {
        if(manager.getPlugin(pluginId).pluginState == PluginState.DISABLED) {
            return Collections.emptyList()
        }
        return finder.find(pluginId)
    }

    override fun <T : Any?> find(type: Class<T>?, pluginId: String?): MutableList<ExtensionWrapper<T>> {
        if(manager.getPlugin(pluginId).pluginState == PluginState.DISABLED) {
            return Collections.emptyList()
        }
        return finder.find(type, pluginId)
    }

    override fun <T : Any?> find(type: Class<T>?): MutableList<ExtensionWrapper<T>> {
        if(type != null && type.isAnnotationPresent(Extension::class.java)) {
            val annotation = type.getAnnotationsByType(Extension::class.java)
        }
        return Collections.emptyList()
    }
}