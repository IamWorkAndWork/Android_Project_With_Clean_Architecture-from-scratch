package app.project.domain.usecase.base

import app.project.domain.executer.PostExecutionThread
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

abstract class AsynchronousRxUseCase(
    val postExecutionThread: PostExecutionThread
) {
    private val compositeDisposables: CompositeDisposable = CompositeDisposable()

    fun dispose() {
        if (!compositeDisposables.isDisposed) {
            compositeDisposables.dispose()
        }
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposables.add(disposable)
    }
}

abstract class ObservableUseCase<Result, Params>(
    postExecutionThread: PostExecutionThread
) : AsynchronousRxUseCase(postExecutionThread) {

    abstract fun buildObservableUseCase(params: Params): Observable<Result>

    fun execute(observer: DisposableObserver<Result>, params: Params) {
        val obsevable = buildObservableUseCase(params = params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.getScheduler())
        addDisposable(obsevable.subscribeWith(observer))
    }

}

abstract class CompletableUseCase<Params>(
    postExecutionThread: PostExecutionThread
) : AsynchronousRxUseCase(postExecutionThread) {

    abstract fun buildCompletableUseCase(params: Params): Completable

    fun execute(observer: DisposableCompletableObserver, params: Params) {
        val completable = buildCompletableUseCase(params = params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.getScheduler())
        addDisposable(completable.subscribeWith(observer))
    }

}


abstract class SingleUseCase<Result, Params>(
    postExecutionThread: PostExecutionThread
) : AsynchronousRxUseCase(postExecutionThread) {

    abstract fun buildSingleUseCase(params: Params): Single<Result>

    fun execute(observer: DisposableSingleObserver<Result>, params: Params) {
        val single = buildSingleUseCase(params = params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.getScheduler())
        addDisposable(single.subscribeWith(observer))
    }

}

abstract class MaybeUseCase<Result, Params>(
    postExecutionThread: PostExecutionThread
) : AsynchronousRxUseCase(postExecutionThread) {

    abstract fun buildMaybeUseCase(params: Params): Maybe<Result>

    fun execute(observer: DisposableMaybeObserver<Result>, params: Params) {
        val maybe = buildMaybeUseCase(params = params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.getScheduler())
        addDisposable(maybe.subscribeWith(observer))
    }

}


