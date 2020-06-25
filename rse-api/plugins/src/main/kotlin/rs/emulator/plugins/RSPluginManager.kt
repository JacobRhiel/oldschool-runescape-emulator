package rs.emulator.plugins

import org.pf4j.ExtensionFactory
import org.pf4j.ExtensionFinder
import org.pf4j.JarPluginManager
import org.pf4j.PluginFactory
import java.nio.file.Paths

object RSPluginManager : JarPluginManager (
    Paths.get(System.getProperty("user.home") + "/rs-emulator/plugins")
) {

    override fun createPluginFactory(): PluginFactory {
        return RSPluginFactory()
    }

    override fun createExtensionFactory(): ExtensionFactory {
        return RSExtensionFactory()
    }

    override fun createExtensionFinder(): ExtensionFinder {
        return RSExtensionFinder(this)
    }
}