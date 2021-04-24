package app.project.domain.usecase

import app.project.domain.entity.UserAccount
import app.project.domain.executer.PostExecutionThread
import app.project.domain.repository.UserAccountRepository
import app.project.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class SignInUseCase(
    private val userAccountRepository: UserAccountRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<UserAccount, SignInUseCase.Params>(postExecutionThread) {

    data class Params(
        val userName: String = "",
        val password: String = ""
    )

    override fun buildSingleUseCase(params: Params): Single<UserAccount> {
        return userAccountRepository.signIn(params.userName, params.password)
    }

}