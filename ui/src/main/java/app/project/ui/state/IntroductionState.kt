package app.project.ui.state

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.project.domain.entity.UserAccount
import app.project.domain.usecase.GetCurrentUserAccoountSyncUseCase
import app.project.domain.usecase.base.SynchronousUseCase
import app.project.ui.base.BaseFragment
import app.project.ui.base.MvRxViewModel
import app.project.ui.common.HiddenBottomBar
import app.project.ui.databinding.FragmentIntroductionBinding
import app.project.ui.mapper.UserAccountUIModelMapper
import app.project.ui.model.UserAccountUIModel
import app.project.ui.navigation.ScreenNavigator
import com.airbnb.mvrx.*
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import org.koin.core.inject

data class IntroductionState(
    val currentUserAccount: UserAccountUIModel? = null
) : MvRxState

class IntroductionViewModel(
    initialState: IntroductionState,
    navigator: ScreenNavigator,
    private val getCurrentUserAccountSyncUseCase: GetCurrentUserAccoountSyncUseCase,
    private val userAccountUIModelMapper: UserAccountUIModelMapper
) : MvRxViewModel<IntroductionState>(initialState, navigator) {

    init {
        getCurrentUserAccount()
    }

    private fun getCurrentUserAccount() {
        getCurrentUserAccountSyncUseCase.execute(
            Unit,
            object : SynchronousUseCase.Callback<UserAccount> {
                override fun onSuccess(result: UserAccount) {
                    handleCurrentUser(result)
                }

                override fun onError(throwable: Throwable) {
                    handleError(throwable)
                }
            }
        )
    }

    private fun handleCurrentUser(userAccount: UserAccount) {
        setState {
            copy(
                currentUserAccount = userAccountUIModelMapper.transformAccount(userAccount)
            )
        }
    }

    companion object : MvRxViewModelFactory<IntroductionViewModel, IntroductionState>,
        KoinComponent {

        override fun create(
            viewModelContext: ViewModelContext,
            state: IntroductionState
        ): IntroductionViewModel {
            return IntroductionViewModel(
                state,
                getKoin().get(),
                getKoin().get(),
                getKoin().get()
            )
        }

    }

}

class IntroductionFragment : BaseFragment(), HiddenBottomBar {

    override val viewModel: IntroductionViewModel by fragmentViewModel()
    private val navigator: ScreenNavigator by inject()
    private var _binding: FragmentIntroductionBinding? = null
    private val binding: FragmentIntroductionBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroductionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectSubscribe(IntroductionState::currentUserAccount) {
            withState(viewModel) {
                if (it.currentUserAccount != null) {
                    navigator.displayNewsFeedAsNavRoot()
                } else {
                    navigator.displayGettingStartedAsNavRoot()
                }
            }
        }
    }

    override fun invalidate() {
    }


}