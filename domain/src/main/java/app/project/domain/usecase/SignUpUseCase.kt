package app.project.domain.usecase

import app.project.domain.entity.UserAccount
import app.project.domain.executer.PostExecutionThread
import app.project.domain.repository.UserAccountRepository
import app.project.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class SignUpUseCase(
    private val userAccountRepository: UserAccountRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<UserAccount, SignUpUseCase.Params>(postExecutionThread) {

    data class Params(
        val username: String,
        val password: String,
        val firstName: String,
        val lastName: String
    )

    override fun buildSingleUseCase(params: Params): Single<UserAccount> {
        return userAccountRepository.signup(
            userName = params.firstName,
            password = params.password,
            firstName = params.firstName,
            lastName = params.lastName,
        )
    }

}