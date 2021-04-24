package app.project.ui.state

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.project.domain.executer.PostExecutionThread
import app.project.domain.usecase.SignInUseCase
import app.project.ui.R
import app.project.ui.UIThread
import app.project.ui.base.BaseFragment
import app.project.ui.base.MvRxViewModel
import app.project.ui.common.HiddenBottomBar
import app.project.ui.databinding.FragmentSignInBinding
import app.project.ui.mapper.UserAccountUIModelMapper
import app.project.ui.model.UserAccountUIModel
import app.project.ui.navigation.ScreenNavigator
import com.airbnb.mvrx.*
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent

data class SignInState(
    val userName: String = "",
    val password: String = "",
    val signinUserAccountUIModel: Async<UserAccountUIModel> = Uninitialized
) : MvRxState

class SignInViewModel(
    initialState: SignInState,
    navigator: ScreenNavigator,
    private val userAccountUIModelMapper: UserAccountUIModelMapper,
    private val signInUseCase: SignInUseCase,
    private val uiThread: PostExecutionThread
) : MvRxViewModel<SignInState>(initialState, navigator) {

    companion object : MvRxViewModelFactory<SignInViewModel, SignInState>, KoinComponent {

        override fun create(
            viewModelContext: ViewModelContext,
            state: SignInState
        ): SignInViewModel {
            return SignInViewModel(
                state,
                getKoin().get(),
                getKoin().get(),
                getKoin().get(),
                getKoin().get(),
            )
        }

    }

    fun signIn() {
        withState { state ->
            signInUseCase.buildSingleUseCase(SignInUseCase.Params(state.userName, state.password))
                .subscribeOn(Schedulers.io())
                .observeOn(uiThread.getScheduler())
                .execute({ userAccountUIModelMapper.transformAccount(it) }) {
                    copy(signinUserAccountUIModel = it)
                }
        }
    }

    fun setUserNameState(userName: String) {
        setState {
            copy(
                userName = userName
            )
        }
    }

    fun setPasswordState(password: String) {
        setState {
            copy(
                password = password
            )
        }
    }

}

class SignInFragment : BaseFragment(), HiddenBottomBar {

    override val viewModel: SignInViewModel by fragmentViewModel()
    private val navigator: ScreenNavigator by inject()
    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
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

        binding.buttonSignIn.setOnClickListener {
            viewModel.signIn()
        }

        viewModel.selectSubscribe(SignInState::signinUserAccountUIModel) {
            withState(viewModel) {
                if (it.signinUserAccountUIModel is Success) {
                    navigator.displayNewsFeedAsNavRoot()
                }
            }
        }

    }

    override fun invalidate() {

    }

}