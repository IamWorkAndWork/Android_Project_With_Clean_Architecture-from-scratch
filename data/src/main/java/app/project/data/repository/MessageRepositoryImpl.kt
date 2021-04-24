package app.project.data.repository

import android.content.Context
import app.project.data.mapper.MessageParseObjectMapper
import app.project.domain.entity.Message
import app.project.domain.repository.MessageRepository
import io.reactivex.Observable
import kotlin.random.Random

class MessageRepositoryImpl(
    private val context: Context,
    private val messageParseObjectMapper: MessageParseObjectMapper
) : MessageRepository {
    override fun getMessage(
        withUserId: String,
        numberOfMessage: Int,
        numberOfSkippedMessage: Int
    ): Observable<List<Message>> {
        return Observable.create { emitter ->
            val messageList = genFakeData()
            emitter.onNext(messageList)
            emitter.onComplete()
        }
    }

    fun genFakeData(): List<Message> {
        val messageList = mutableListOf<Message>()
        for (i in 0..50) {
            val randomSender = Random.nextInt(1, 3)
            val receiver = when (randomSender) {
                1 -> "2"
                else -> "1"
            }
            val contentMessage = when (randomSender) {
                1 -> "hello ${randomSender} to ${receiver} $i"
                else -> "world ${randomSender} to ${receiver} $i"
            }
            val message = Message(
                id = i.toString(),
                sender = randomSender.toString(),
                receiver = receiver,
                content = contentMessage
            )
            messageList.add(message)
        }
        return messageList
    }
}