package app.project.domain.repository

import app.project.domain.entity.Message
import io.reactivex.Observable

interface MessageRepository {
    fun getMessage(
        withUserId: String,
        numberOfMessage: Int,
        numberOfSkippedMessage: Int,
    ): Observable<List<Message>>
}