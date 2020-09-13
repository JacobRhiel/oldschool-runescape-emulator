package rs.emulator.database.factory

import org.hibernate.SessionFactory

/**
 *
 * @author Chk
 */
interface IDatabase
{

    val factory: SessionFactory

}