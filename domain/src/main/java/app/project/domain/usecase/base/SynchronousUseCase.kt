package app.project.domain.usecase.base

interface SynchronousUseCase<Result, Param> {
    interface Callback<Result> {
        fun onSuccess(result: Result)
        fun onError(throwable: Throwable)
    }

    fun execute(param: Param, callback: Callback<Result>)
}