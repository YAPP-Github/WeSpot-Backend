package com.wespot.message

import com.wespot.message.MessageTimeValidator.validateMessageSendTime
import com.wespot.message.MessageTimeValidator.validateMessageUpdateTime
import com.wespot.user.User
import java.time.LocalDateTime

data class Message(
    val id: Long,
    val content: String,
    val senderId: Long,
    val senderName: String,
    val receiverId: Long,
    val isReceiverRead: Boolean?,
    val messageType: MessageType,
    val readAt: LocalDateTime?,
    val isSend: Boolean,
    val sendAt: LocalDateTime?,
    val receivedAt: LocalDateTime?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {

    fun updateMessage(
        content: String,
        modifier: User,
        receiverId: Long,
        senderName: String
    ): Message {
        validateMessageOwner(modifier)
        require(senderId != receiverId) { "본인이 메시지를 보낼 수 없습니다." }
        validateMessageUpdateTime()
        val message = Message(
            id = id,
            content = content,
            senderId = senderId,
            senderName = senderName,
            messageType = MessageType.SENT,
            receiverId = receiverId,
            isReceiverRead = isReceiverRead,
            readAt = readAt,
            isSend = isSend,
            sendAt = LocalDateTime.now(),
            createdAt = createdAt,
            updatedAt = LocalDateTime.now(),
            receivedAt = receivedAt,
        )
        message.validateMessageReceiver()

        return message
    }

    fun readMessage(
        user: User
    ): Message {
        val message = Message(
            id = id,
            content = content,
            senderId = senderId,
            senderName = senderName,
            messageType = MessageType.SENT,
            receiverId = receiverId,
            isReceiverRead = true,
            readAt = LocalDateTime.now(),
            isSend = isSend,
            sendAt = sendAt,
            createdAt = createdAt,
            updatedAt = LocalDateTime.now(),
            receivedAt = receivedAt
        )
        message.validateSentMessage(user)
        return message
    }

    fun validateMessageOwner(loginUser: User) {
        require(messageType == MessageType.SENT) { "보낸 메시지만 수정이 가능합니다." }
        require(senderId == loginUser.id) { "메시지 작성자만 수정할 수 있습니다." }
    }

    fun validateMessageReceiver() {
        require(receiverId != senderId) { "본인이 메시지를 보낼 수 없습니다." }
    }

    fun validateSentMessage(loginUser: User) {
        require(receiverId != loginUser.id) { "본인이 받은 메시지만 읽을 수 있습니다." }
        require(messageType != MessageType.RECEIVED) { "받은 메시지만 읽을 수 있습니다." }
    }

    companion object {

        fun sendMessage(
            content: String,
            receiverId: Long,
            senderId: Long,
            senderName: String
        ): Message {
            validateMessageSendTime()
            val message = Message(
                id = 0L,
                content = content,
                senderId = senderId,
                senderName = senderName,
                messageType = MessageType.SENT,
                receiverId = receiverId,
                isReceiverRead = false,
                readAt = null,
                isSend = false,
                sendAt = LocalDateTime.now(),
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                receivedAt = null,
            )
            message.validateMessageReceiver()

            return message
        }

    }
}
