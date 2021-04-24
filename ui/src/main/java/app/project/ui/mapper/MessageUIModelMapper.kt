package app.project.ui.mapper

import app.project.domain.entity.Message
import app.project.ui.model.MessageUIModel

class MessageUIModelMapper {

    fun transform(message: Message): MessageUIModel {
        return MessageUIModel(
            id = message.id,
            sender = message.sender,
            receiver = message.receiver,
            content = message.content
        )
    }

    fun transform(messages: List<Message>): List<MessageUIModel> {
        return messages.map {
            transform(it)
        }
    }

}