package rs.emulator.database.entry

import org.hibernate.SessionFactory
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.database.service.JDBCPoolingService
import rs.emulator.database.transaction.Transactor

/**
 *
 * @author Chk
 */
interface Entry : KoinComponent, Transactor
{

    private val service: JDBCPoolingService
        get() = get()

    override val factory: SessionFactory
        get() = service.factory

}