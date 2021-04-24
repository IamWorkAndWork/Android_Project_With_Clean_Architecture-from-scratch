package app.project.ui.state

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import app.project.domain.executer.PostExecutionThread
import app.project.domain.usecase.GetMessageUseCase
import app.project.ui.base.BaseEpoxyFragment
import app.project.ui.base.MvRxEpoxyController
import app.project.ui.base.MvRxViewModel
import app.project.ui.databinding.FragmentMessageBinding
import app.project.ui.mapper.MessageUIModelMapper
import app.project.ui.model.MessageUIModel
import app.project.ui.navigation.ScreenNavigator
import com.airbnb.mvrx.*
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent

data class MessageState(
    val messages: Async<List<MessageUIModel>> = Uninitialized
) : MvRxState

class MessageViewModel(
    initialState: MessageState,
    navigator: ScreenNavigator,
    private val getMessageUseCase: GetMessageUseCase,
    private val messageUIModelMapper: MessageUIModelMapper,
    private val uiThread: PostExecutionThread
) : MvRxViewModel<MessageState>(initialState, navigator) {

    fun getMessages(withUserId: String, numberOfMessage: Int, numberOfSkippedMessage: Int) {

        withState(this) {

            val messages = it.messages()

            getMessageUseCase.buildObservableUseCase(
                GetMessageUseCase.Params(withUserId, numberOfMessage, numberOfSkippedMessage)
            )
                .subscribeOn(Schedulers.io())
                .observeOn(uiThread.getScheduler())
                .execute({
                    messageUIModelMapper.transform(it)
                }) {

                    if (it !is Uninitialized && it !is Loading && it !is Fail) {
                        val messageList = mutableListOf<MessageUIModel>()
                        it.invoke()?.let { messageUiList ->
                            messageList.addAll(messageUiList)
                        }
                        messages.let { messageUIList ->
                            if (messageUIList != null) {
                                messageList.addAll(messageUIList)
                            }
                        }
                        copy(messages = Success(messageList))
                    } else {
                        copy(messages = it)
                    }
                }
        }
    }

    companion object : MvRxViewModelFactory<MessageViewModel, MessageState>, KoinComponent {

        override fun create(
            viewModelContext: ViewModelContext,
            state: MessageState
        ): MessageViewModel {
            return MessageViewModel(
                state,
                getKoin().get(),
                getKoin().get(),
                getKoin().get(),
                getKoin().get()
            )
        }
    }

}

class MessageFragment : BaseEpoxyFragment() {

    override val viewModel: MessageViewModel by fragmentViewModel()
    private var _binding: FragmentMessageBinding? = null
    private val binding: FragmentMessageBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.messagesEpoxyRecycleView

        recyclerView.setController(controller = epoxyController)

        recyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)


                if (!recyclerView.canScrollVertically(-1) &&
                    newState == RecyclerView.SCROLL_STATE_IDLE
                ) {

                    Toast.makeText(context, "at top !!!", Toast.LENGTH_LONG).show()

                    withState(viewModel) {
                        if (it.messages !is Loading) {
                            viewModel.getMessages(
                                "1",
                                30,
                                it.messages()?.size ?: 0
                            )
                        }
                    }

                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        withState(viewModel) {
            viewModel.getMessages(
                "1",
                30,
                it.messages()?.size ?: 0
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun epoxyController(): MvRxEpoxyController {
        return simpleController(viewModel) { state ->

            state.messages()?.forEach {

                messageItem {
                    id(it.id)

                    if (it.sender == "1") {
                        heartMessage(it.content)
                        information("")
                        myMessage("")
                    }
                    else {
                        myMessage(it.content)
                        information("")
                        heartMessage("")
                    }
                }

            }

        }
    }

}