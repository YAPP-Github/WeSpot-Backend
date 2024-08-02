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
    val isReceiverRead: Boolean,
    val isAnonymous: Boolean,
    val messageType: MessageType,
    val readAt: LocalDateTime?,
    val isSend: Boolean,
    val sendAt: LocalDateTime?,
    val receivedAt: LocalDateTime?,
    val isReported: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val isDeleted: Boolean,
    val deletedAt: LocalDateTime?
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
            isAnonymous = isAnonymous,
            isReceiverRead = isReceiverRead,
            readAt = readAt,
            isSend = isSend,
            sendAt = LocalDateTime.now(),
            isReported = isReported,
            createdAt = createdAt,
            updatedAt = LocalDateTime.now(),
            receivedAt = receivedAt,
            isDeleted = isDeleted,
            deletedAt = deletedAt
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
            messageType = messageType,
            receiverId = receiverId,
            isAnonymous = isAnonymous,
            isReceiverRead = true,
            readAt = LocalDateTime.now(),
            isSend = isSend,
            sendAt = sendAt,
            isReported = isReported,
            createdAt = createdAt,
            updatedAt = LocalDateTime.now(),
            receivedAt = receivedAt,
            isDeleted = isDeleted,
            deletedAt = deletedAt
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
        require(receiverId == loginUser.id) { "본인이 받은 메시지만 읽을 수 있습니다." }
        require(messageType == MessageType.RECEIVED) { "받은 메시지만 읽을 수 있습니다." }
    }

    fun validateDeleteMessage(loginUser: User) {
        require(senderId == loginUser.id) { "메시지를 삭제할 권한이 없습니다." }
    }

    fun validateReadMessage(loginUser: User) {
        require(senderId == loginUser.id || receiverId == loginUser.id) { "메시지를 읽을 수 있는 권한이 없습니다." }
    }

    fun validateReceivedMessage(loginUser: User) {
        require(messageType == MessageType.RECEIVED) { "받은 메시지만 차단이 가능합니다." }
        require(senderId != loginUser.id) { "받은 메시지만 차단이 가능합니다" }
        require(receiverId == loginUser.id) { "받은 메시지만 차단이 가능합니다" }
    }

    fun reported() =
        this.copy(
            isReported = true,
            updatedAt = LocalDateTime.now()
        )


    fun softDelete(loginUser: User) : Message{
        validateDeleteMessage(loginUser)
        return this.copy(
            isDeleted = true,
            deletedAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    companion object {

        fun sendMessage(
            content: String,
            receiverId: Long,
            senderId: Long,
            senderName: String,
            isAnonymous: Boolean
        ): Message {
            validateMessageSendTime()
            val message = Message(
                id = 0L,
                content = content,
                senderId = senderId,
                senderName = senderName,
                messageType = MessageType.SENT,
                receiverId = receiverId,
                isAnonymous = isAnonymous,
                isReceiverRead = false,
                readAt = null,
                isSend = false,
                sendAt = LocalDateTime.now(),
                isReported = false,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                receivedAt = null,
                isDeleted = false,
                deletedAt = null
            )
            message.validateMessageReceiver()

            return message
        }

    }
}
