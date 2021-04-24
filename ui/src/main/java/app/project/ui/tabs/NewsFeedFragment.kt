package app.project.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.project.ui.base.BaseFragment
import app.project.ui.base.MvRxViewModel
import app.project.ui.databinding.FragmentNewsFeedBinding
import app.project.ui.navigation.ScreenNavigator
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.airbnb.mvrx.fragmentViewModel
import org.koin.core.KoinComponent

data class NewsFeedState(val dummy: String? = null) : MvRxState

class NewsFeedViewModel(
    initialState: NewsFeedState,
    navigator: ScreenNavigator
) : MvRxViewModel<NewsFeedState>(initialState, navigator) {

    companion object : MvRxViewModelFactory<NewsFeedViewModel, NewsFeedState>, KoinComponent {
        override fun create(
            viewModelContext: ViewModelContext,
            state: NewsFeedState
        ): NewsFeedViewModel {
            return NewsFeedViewModel(
                state,
                getKoin().get(),
            )
        }
    }

}

class NewsFeedFragment : BaseFragment() {

    override val viewModel: NewsFeedViewModel by fragmentViewModel()

    private var _binding: FragmentNewsFeedBinding? = null
    private val binding: FragmentNewsFeedBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun invalidate() {

    }
}