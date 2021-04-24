package app.project.domain.usecase

import app.project.domain.entity.UserAccount
import app.project.domain.repository.UserAccountRepository
import app.project.domain.usecase.base.SynchronousUseCase

class GetCurrentUserAccoountSyncUseCase(
    private val userAccountRepository: UserAccountRepository
) : SynchronousUseCase<UserAccount, Unit> {

    override fun execute(param: Unit, callback: SynchronousUseCase.Callback<UserAccount>) {
        return userAccountRepository.getCurrentUserAccountSync(callback)
    }

}