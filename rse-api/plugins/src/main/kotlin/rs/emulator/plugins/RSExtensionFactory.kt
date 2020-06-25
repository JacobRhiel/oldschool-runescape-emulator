package rs.emulator.plugins

import org.pf4j.ExtensionFactory

/**
 *
 * @author javatar
 */

internal class RSExtensionFactory : ExtensionFactory {
    override fun <T : Any?> create(extensionClass: Class<T>?): T? {
        return extensionClass?.getConstructor()?.newInstance()
    }
}