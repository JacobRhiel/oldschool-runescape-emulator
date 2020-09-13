package rs.emulator.entity.social

import org.pf4j.Plugin
import org.pf4j.PluginWrapper
import rs.emulator.entity.social.database.SocialDatabaseTable
import rs.emulator.database.annotation.DatabaseMetaData
import rs.emulator.database.factory.DatabaseFactory

/**
 *
 * @author Chk
 */
@DatabaseMetaData(tableTypes = [ SocialDatabaseTable::class ])
class SocialPlugin(wrapper: PluginWrapper) : Plugin(wrapper)
{

    override fun start()
    {

        val database = DatabaseFactory.create(this) {

            this.username("javatar")

            this.password("david@aka@javatar")

            this.databaseName("runescape-emulator")

            this.address("140.82.2.221")

            this.classLoader(wrapper.pluginClassLoader)

        }

    }

}