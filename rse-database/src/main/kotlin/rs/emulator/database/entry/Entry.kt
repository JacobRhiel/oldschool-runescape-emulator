package rs.emulator.database.entry

import org.hibernate.SessionFactory
import rs.emulator.database.transaction.Transactor

/**
 *
 * @author Chk
 */
abstract class Entry : Transactor
{

    private lateinit var sessionFactory: SessionFactory

    override var factory: SessionFactory
        get() = sessionFactory
        set(value)
        {
            sessionFactory = value
        }

}