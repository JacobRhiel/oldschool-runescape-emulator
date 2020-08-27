package rs.emulator.entity.social

import org.pf4j.Plugin
import org.pf4j.PluginWrapper
import rs.emulator.api.database.annotation.DatabaseMetaData
import rs.emulator.api.database.factory.DatabaseFactory
import rs.emulator.entity.social.database.SocialDatabaseTable
import rs.emulator.api.database.factory.withTransaction

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

        val table = SocialDatabaseTable(0)

        println(SocialDatabaseTable::class.java)

        database.withTransaction { tx ->

            println(tx.javaClass.classLoader.definedPackages.toList().toTypedArray().contentDeepToString())

            //println(tx.get(SocialDatabaseTable::class.java, 0))

            //(tx.get(SocialDatabaseTable::class.java, 0) ?: table.also { tx.save(it); this.commit() })
          //  tx.update(table)

           // this.commit()

        }

    }

}