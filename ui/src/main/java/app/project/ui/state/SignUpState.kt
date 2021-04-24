package app.project.ui.state

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.project.domain.executer.PostExecutionThread
import app.project.domain.usecase.SignUpUseCase
import app.project.ui.UIThread
import app.project.ui.base.BaseFragment
import app.project.ui.base.MvRxViewModel
import app.project.ui.common.HiddenBottomBar
import app.project.ui.databinding.FragmentSignUpBinding
import app.project.ui.mapper.UserAccountUIModelMapper
import app.project.ui.model.UserAccountUIModel
import app.project.ui.navigation.ScreenNavigator
import com.airbnb.mvrx.*
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent

data class SignUpState(
    val userName: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val signupUserAccountUIModel: Async<UserAccountUIModel> = Uninitialized
) : MvRxState

class SignUpViewModel(
    initialState: SignUpState,
    navigator: ScreenNavigator,
    private val signUpUseCase: SignUpUseCase,
    private val userAccountUIModelMapper: UserAccountUIModelMapper,
    private val uiThread: PostExecutionThread
) : MvRxViewModel<SignUpState>(initialState, navigator) {

    fun signUp() {
        withState { state ->
            signUpUseCase.buildSingleUseCase(
                SignUpUseCase.Params(
                    username = state.userName,
                    password = state.password,
                    firstName = state.firstName,
                    lastName = state.lastName
                )
            ).subscribeOn(Schedulers.io())
                .observeOn(uiThread.getScheduler())
                .execute({
                    userAccountUIModelMapper.transformAccount(it)
                }) {
                    copy(signupUserAccountUIModel = it)
                }
        }
    }

    fun setUserNameState(userName: String) {
        setState {
            copy(userName = userName)
        }
    }

    fun setPasswordState(password: String) {
        setState {
            copy(password = password)
        }
    }

    fun setFirstNameState(firstName: String) {
        setState {
            copy(firstName = firstName)
        }
    }

    fun setLastNameState(lastName: String) {
        setState {
            copy(lastName = lastName)
        }
    }

    companion object : MvRxViewModelFactory<SignUpViewModel, SignUpState>, KoinComponent {

        override fun create(
            viewModelContext: ViewModelContext,
            state: SignUpState
        ): SignUpViewModel {
            return SignUpViewModel(
                state,
                getKoin().get(),
                getKoin().get(),
                getKoin().get(),
                getKoin().get()
            )
        }

    }

}


class SignupFragment : BaseFragment(), HiddenBottomBar {

    override val viewModel: SignUpViewModel by fragmentViewModel()

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextUserName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    viewModel.setUserNameState(it.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        binding.editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    viewModel.setPasswordState(it.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        binding.editTextFirstName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    viewModel.setFirstNameState(it.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        binding.editTextLastName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    viewModel.setLastNameState(it.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        binding.buttonSignIn.setOnClickListener {
            viewModel.signUp()
        }

        viewModel.selectSubscribe(SignUpState::signupUserAccountUIModel) {
            withState(viewModel) { state ->
                if (state.signupUserAccountUIModel is Success) {
                    navigator.displayNewsFeedAsNavRoot()
                }
            }
        }
    }


    override fun invalidate() {

    }

    private val navigator: ScreenNavigator by inject()


}