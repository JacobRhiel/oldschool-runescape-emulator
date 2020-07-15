package rs.emulator.plugins

import org.pf4j.ExtensionFactory
import org.pf4j.ExtensionPoint
import org.pf4j.JarPluginManager
import org.pf4j.PluginFactory
import java.nio.file.Paths

object RSPluginManager : JarPluginManager (
    Paths.get(System.getProperty("user.home") + "/rs-emulator/plugins")
) {

    inline fun <reified T : ExtensionPoint> getExtensions(): List<T> {
        return this.getExtensions(T::class.java)
    }

    override fun createPluginFactory(): PluginFactory {
        return RSPluginFactory()
    }

    override fun createExtensionFactory(): ExtensionFactory {
        return RSExtensionFactory()
    }
}