package app.project.domain.usecase

import app.project.domain.entity.Message
import app.project.domain.executer.PostExecutionThread
import app.project.domain.repository.MessageRepository
import app.project.domain.usecase.base.ObservableUseCase
import io.reactivex.Observable

class GetMessageUseCase(
    private val messageRepository: MessageRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Message>, GetMessageUseCase.Params>(postExecutionThread) {

    data class Params(
        val withUserId: String = "",
        val numberOfMessage: Int = 0,
        val numberOgSkippedMessage: Int = 0
    )

    override fun buildObservableUseCase(params: Params): Observable<List<Message>> {
        return messageRepository.getMessage(
            params.withUserId, params.numberOfMessage, params.numberOgSkippedMessage
        )
    }

}