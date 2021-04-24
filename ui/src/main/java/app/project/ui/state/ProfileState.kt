package app.project.ui.state

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.project.domain.executer.PostExecutionThread
import app.project.domain.usecase.SignOutUseCase
import app.project.ui.UIThread
import app.project.ui.base.BaseFragment
import app.project.ui.base.MvRxViewModel
import app.project.ui.databinding.FragmentProfileBinding
import app.project.ui.navigation.ScreenNavigator
import com.airbnb.mvrx.*
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent

data class ProfileState(
    val isSignedOut: Async<Unit> = Uninitialized
) : MvRxState

class ProfileViewModel(
    initialState: ProfileState,
    navigator: ScreenNavigator,
    private val signOutUseCase: SignOutUseCase,
    private val uiThread: PostExecutionThread
) : MvRxViewModel<ProfileState>(initialState, navigator) {

    fun signOut() {
        signOutUseCase.buildCompletableUseCase(Unit)
            .subscribeOn(Schedulers.io())
            .observeOn(uiThread.getScheduler())
            .execute {
                copy(isSignedOut = it)
            }
    }

    companion object : MvRxViewModelFactory<ProfileViewModel, ProfileState>, KoinComponent {
        override fun create(
            viewModelContext: ViewModelContext,
            state: ProfileState
        ): ProfileViewModel {
            return ProfileViewModel(
                state,
                getKoin().get(),
                getKoin().get(),
                getKoin().get()
            )
        }
    }

}

class ProfileFragment : BaseFragment() {

    override val viewModel: ProfileViewModel by fragmentViewModel()
    private val navigator: ScreenNavigator by inject()

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignOut.setOnClickListener {
            viewModel.signOut()
        }

        viewModel.selectSubscribe(ProfileState::isSignedOut) {
            withState(viewModel) {
                if (it.isSignedOut is Success) {
                    navigator.resetMainScreen()
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun invalidate() {

    }


}