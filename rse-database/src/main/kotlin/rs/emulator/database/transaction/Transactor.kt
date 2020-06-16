package rs.emulator.database.transaction

import org.hibernate.Session
import org.hibernate.SessionFactory

/**
 *
 * @author Chk
 */
interface Transactor
{

    var factory: SessionFactory

    private val sessions: ThreadLocal<Session>
        get() = ThreadLocal()

    fun transact() : TransactionLayer = TransactionLayer(sessions, factory)

}