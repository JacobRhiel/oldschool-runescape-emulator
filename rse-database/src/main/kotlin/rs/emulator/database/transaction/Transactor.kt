package rs.emulator.database.transaction

import org.hibernate.Session
import org.hibernate.SessionFactory

/**
 *
 * @author Chk
 */
interface Transactor
{

    val factory: SessionFactory

    private val sessions: ThreadLocal<Session>
        get() = ThreadLocal()

    fun transact() : TransactionLayer = TransactionLayer(sessions, factory)

    fun save(transactor: Transactor)
    {

        val session = factory.openSession()

        val tx = session.beginTransaction()

        session.saveOrUpdate(transactor)

        tx.commit()

    }

    fun get(transactor: Transactor, id: Any): Transactor
    {

        val session = factory.openSession()

        val tx = session.beginTransaction()

        val response = session.get(transactor::class.java, id)

        if(response == null)
            session.persist(transactor)

        tx.commit()

        return session.load(transactor::class.java, id)

    }

}