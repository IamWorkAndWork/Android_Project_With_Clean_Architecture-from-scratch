package app.project.domain.repository

import app.project.domain.entity.UserAccount
import app.project.domain.usecase.base.SynchronousUseCase
import io.reactivex.Completable
import io.reactivex.Single

interface UserAccountRepository {
    fun getCurrentUserAccountSync(callback: SynchronousUseCase.Callback<UserAccount>)

    fun signIn(userName: String, password: String): Single<UserAccount>

    fun signOut(): Completable

    fun signup(
        userName: String,
        password: String,
        firstName: String,
        lastName: String
    ): Single<UserAccount>
}