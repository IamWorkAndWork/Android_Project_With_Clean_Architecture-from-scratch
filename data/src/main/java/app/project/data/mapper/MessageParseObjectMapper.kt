package app.project.data.mapper

import app.project.domain.entity.Message
import com.parse.ParseObject

class MessageParseObjectMapper {

    fun transform(messageParseObject: ParseObject): Message {
        return Message(
            id = messageParseObject.objectId,
            sender = messageParseObject.getString("sender") ?: "",
            receiver = messageParseObject.getString("receiver") ?: "",
            content = messageParseObject.getString("content") ?: ""
        )
    }

    fun transform(messageParseObject: List<ParseObject>): List<Message> {
        return messageParseObject.map { parseObject ->
            transform(parseObject)
        }
    }

}