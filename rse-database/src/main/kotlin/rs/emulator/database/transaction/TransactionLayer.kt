package rs.emulator.database.transaction

import org.hibernate.*
import org.hibernate.resource.transaction.spi.TransactionStatus
import java.io.Closeable
import java.lang.Exception
import javax.persistence.EntityManager
import kotlin.concurrent.getOrSet

/**
 *
 * @author Chk
 */
class TransactionLayer(
    private val sessions: ThreadLocal<Session>,
    private val factory: SessionFactory
)
    : Closeable
{

    private val session: Session = sessions.getOrSet { factory.openSession() }

    private val transaction: Transaction = session.beginTransaction()

    fun fetchManager() : EntityManager = session

    override fun close()
    {

        try
        {

            session.flush()

        }
        catch(e: Exception)
        {

            transaction.rollback()

            println("rolling back")

        }
        finally
        {

            sessions.set(null)

            if(transaction.status != TransactionStatus.ROLLED_BACK)
            {
                transaction.commit()
                println("committing")
            }

            println("closing")

            session.close()

        }

    }

}