package rs.emulator.plugins

import org.pf4j.*
import java.nio.file.Path
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
}