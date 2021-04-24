package app.project.domain.usecase

import app.project.domain.repository.CommunicationRepository
import app.project.domain.usecase.base.SynchronousUseCase
import java.lang.Exception

class initializeCommunicationUseCase(
    private val communicationRepository: CommunicationRepository
) : SynchronousUseCase<Unit, Unit> {
    override fun execute(param: Unit, callback: SynchronousUseCase.Callback<Unit>) {
        try {
            communicationRepository.initialzeCommunication()
            callback.onSuccess(Unit)
        } catch (ex: Exception) {
            callback.onError(ex)
        }
    }
}