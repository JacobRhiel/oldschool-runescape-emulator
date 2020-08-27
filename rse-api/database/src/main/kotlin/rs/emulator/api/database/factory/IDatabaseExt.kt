package rs.emulator.api.database.factory

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.hibernate.Session
import org.hibernate.Transaction

/**
 *
 * @author Chk
 */
inline fun <reified T> Database.withTransaction(crossinline block: Transaction.(Session) -> T): T
{
    return Observable.just(factory)
        .observeOn(Schedulers.io())
        .map { it.openSession() }
        .map { it to it.beginTransaction() }
        .switchMap { p ->
            Observable.just(block(p.second, p.first))
                .doOnError {
                    p.second.rollback()
                    it.printStackTrace()
                }
                .doFinally { p.first.close() }
        }.singleElement().blockingGet()
}