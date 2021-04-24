package app.project.ui.state

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.project.ui.base.BaseFragment
import app.project.ui.base.MvRxViewModel
import app.project.ui.common.HiddenBottomBar
import app.project.ui.databinding.FragmentGettingstartedBinding
import app.project.ui.navigation.ScreenNavigator
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.airbnb.mvrx.fragmentViewModel
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent

data class GettingStartedState(
    val dummy: String? = null
) : MvRxState

class GettingStartedViewModel(
    initialState: GettingStartedState,
    navigator: ScreenNavigator
) : MvRxViewModel<GettingStartedState>(initialState, navigator) {

    companion object : MvRxViewModelFactory<GettingStartedViewModel, GettingStartedState>,
        KoinComponent {

        override fun create(
            viewModelContext: ViewModelContext,
            state: GettingStartedState
        ): GettingStartedViewModel {
            return GettingStartedViewModel(
                state,
                getKoin().get()
            )
        }

    }

}

class GettingStartedFragment : BaseFragment(), HiddenBottomBar {

    override val viewModel: GettingStartedViewModel by fragmentViewModel()
    private val navigator: ScreenNavigator by inject()

    private var _binding: FragmentGettingstartedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGettingstartedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignIn.setOnClickListener {
            navigator.navigateToSignIn()
        }

        binding.buttonSignUp.setOnClickListener {
            navigator.navigateToSignUp()
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun invalidate() {
    }
}