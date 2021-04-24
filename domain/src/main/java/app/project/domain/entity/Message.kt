package app.project.domain.entity

data class Message(
    val id: String,
    val sender: String,
    val receiver: String,
    val content: String,
)