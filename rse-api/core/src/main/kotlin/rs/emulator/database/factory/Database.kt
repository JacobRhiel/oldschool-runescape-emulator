package rs.emulator.database.factory

import org.hibernate.SessionFactory
import rs.emulator.database.annotation.DatabaseMetaData
import java.io.Closeable

/**
 *
 * @author Chk
 */
class Database(val metaData: DatabaseMetaData,
               override val factory: SessionFactory)
    : IDatabase, Closeable
{

    override fun close()
    {

        val currentSession = factory.currentSession

        if(currentSession.isConnected && currentSession.isOpen)
            currentSession.flush()
        
        factory.close()

    }

}