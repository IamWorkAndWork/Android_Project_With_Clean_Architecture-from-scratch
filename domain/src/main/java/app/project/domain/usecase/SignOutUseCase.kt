package app.project.domain.usecase

import app.project.domain.executer.PostExecutionThread
import app.project.domain.repository.UserAccountRepository
import app.project.domain.usecase.base.CompletableUseCase
import io.reactivex.Completable

class SignOutUseCase(
    private val userAccountRepository: UserAccountRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<Unit>(postExecutionThread) {

    override fun buildCompletableUseCase(params: Unit): Completable {
        return userAccountRepository.signOut()
    }

}