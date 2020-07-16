package rs.emulator.reactive

import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

/**
 *
 * @author javatar
 */

abstract class ReactiveProperty<T> {

    protected val observable = PublishSubject.create<T>()
    private var disposable: Disposable? = null

    fun subscribe(onNext: (T) -> Unit) {
        disposable = observable.subscribe(onNext)
    }

    fun dispose() {
        disposable?.dispose()
    }

}