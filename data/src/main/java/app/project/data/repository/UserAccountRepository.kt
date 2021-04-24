package app.project.data.repository

import android.content.Context
import app.project.data.ErrorValue
import app.project.data.R
import app.project.data.mapper.ParseUserMapper
import app.project.domain.entity.UserAccount
import app.project.domain.repository.UserAccountRepository
import app.project.domain.usecase.base.SynchronousUseCase
import com.parse.ParseUser
import io.reactivex.Completable
import io.reactivex.Single

class UserAccountRepositoryImpl(
    private val context: Context,
    private val parseUserMapper: ParseUserMapper
) : UserAccountRepository {

    override fun getCurrentUserAccountSync(callback: SynchronousUseCase.Callback<UserAccount>) {

        callback.onSuccess(
            UserAccount(
                id = "1",
                username = "John",
                email = "email",
                firstname = "Thee",
                lastname = "Rapong"
            )
        )

//        val currentUser = ParseUser.getCurrentUser()
//        if (currentUser != null) {
//            callback.onSuccess(parseUserMapper.transform(currentUser))
//        } else {
//            callback.onError(
//                ErrorValue.CurrentUserNotFound(
//                    TAG,
//                    context.getString(R.string.current_user_not_found)
//                )
//            )
//        }
    }

    override fun signIn(userName: String, password: String): Single<UserAccount> {
        return Single.create { emitter ->

            emitter.onSuccess(
                UserAccount(
                    id = "1",
                    username = "John",
                    email = "email",
                    firstname = "Thee",
                    lastname = "Rapong"
                )
            )
//            ParseUser.logInInBackground(userName, password) { user, e ->
//                if (user != null) emitter.onSuccess(parseUserMapper.transform(user))
//                else emitter.onError(
//                    ErrorValue.CanNotSignIn(
//                        TAG,
//                        context.getString(R.string.can_not_sign_in),
//                        e
//                    )
//                )
//            }
        }
    }

    override fun signOut(): Completable {
        return Completable.create { emitter ->

            emitter.onComplete()

//            ParseUser.logOutInBackground { e ->
//                if (e == null) emitter.onComplete()
//                else emitter.onError(
//                    ErrorValue.CanNotSignOut(
//                        TAG, context.getString(R.string.can_not_sign_out),
//                        e
//                    )
//                )
//            }
        }
    }

    override fun signup(
        userName: String,
        password: String,
        firstName: String,
        lastName: String
    ): Single<UserAccount> {
        return Single.create { emitter ->

            emitter.onSuccess(
                UserAccount(
                    id = "1",
                    username = "John",
                    email = "email",
                    firstname = "Thee",
                    lastname = "Rapong"
                )
            )

//            val parseUser = ParseUser().apply {
//                this.username = userName
//                this.setPassword(password)
//                this.put("firstName", firstName)
//                this.put("lastName", lastName)
//            }

//            parseUser.signUpInBackground { e ->
//                if (e == null) emitter.onSuccess(parseUserMapper.transform(parseUser))
//                else emitter.onError(
//                    ErrorValue.CanNotSignUp(
//                        TAG,
//                        context.getString(R.string.can_not_sign_up),
//                        e
//                    )
//                )
//            }
        }
    }
}