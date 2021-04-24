package app.project.domain.usecase.base

import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver

open class ObservableObserver<T> : DisposableObserver<T>() {
    override fun onNext(t: T) {
    }

    override fun onError(e: Throwable) {
    }

    override fun onComplete() {
    }

}

open class CompletableObserver : DisposableCompletableObserver() {
    override fun onComplete() {
    }

    override fun onError(e: Throwable) {
    }
}

open class SingleObserver<T> : DisposableSingleObserver<T>() {
    override fun onSuccess(t: T) {
    }

    override fun onError(e: Throwable) {
    }
}

open class MaybeObserver<T> : DisposableMaybeObserver<T>() {
    override fun onSuccess(t: T) {
    }

    override fun onError(e: Throwable) {
    }

    override fun onComplete() {
    }
}