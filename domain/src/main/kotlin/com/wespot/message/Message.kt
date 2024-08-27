package com.wespot.message

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.message.MessageTimeValidator.validateMessageSendTime
import com.wespot.message.MessageTimeValidator.validateMessageUpdateTime
import com.wespot.user.User
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class Message(
    val id: Long,
    val content: MessageContent,
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
    val isSenderDeleted: Boolean,
    val senderDeletedAt: LocalDateTime?,
    val isReceiverDeleted: Boolean,
    val receiverDeletedAt: LocalDateTime?
) {

    fun updateMessage(
        content: String,
        modifier: User,
        receiverId: Long,
        senderName: String
    ): Message {
        validateRegulationUser(modifier)
        validateMessageOwner(modifier)
        require(senderId != receiverId) {
            throw CustomException(
                HttpStatus.BAD_REQUEST,
                ExceptionView.TOAST,
                "본인이 메시지를 보낼 수 없습니다."
            )
        }
        validateMessageUpdateTime()
        val message = Message(
            id = id,
            content = MessageContent.from(content),
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
            isSenderDeleted = isSenderDeleted,
            senderDeletedAt = senderDeletedAt,
            isReceiverDeleted = isReceiverDeleted,
            receiverDeletedAt = receiverDeletedAt
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
            isSenderDeleted = isSenderDeleted,
            senderDeletedAt = senderDeletedAt,
            isReceiverDeleted = isReceiverDeleted,
            receiverDeletedAt = receiverDeletedAt
        )
        message.validateSentMessage(user)
        return message
    }

    fun validateMessageOwner(loginUser: User) {
        require(messageType == MessageType.SENT) {
            throw CustomException(
                HttpStatus.BAD_REQUEST,
                ExceptionView.TOAST,
                "보낸 메시지만 수정이 가능합니다."
            )
        }
        require(senderId == loginUser.id) {
            throw CustomException(
                HttpStatus.FORBIDDEN,
                ExceptionView.TOAST,
                "메시지 작성자만 수정할 수 있습니다."
            )
        }
    }

    fun validateMessageReceiver() {
        require(receiverId != senderId) {
            throw CustomException(
                HttpStatus.BAD_REQUEST,
                ExceptionView.TOAST,
                "본인이 메시지를 보낼 수 없습니다."
            )
        }
    }

    fun validateSentMessage(loginUser: User) {
        require(receiverId == loginUser.id) {
            throw CustomException(
                HttpStatus.FORBIDDEN,
                ExceptionView.TOAST,
                "본인이 받은 메시지만 읽을 수 있습니다."
            )
        }
        require(messageType == MessageType.RECEIVED) {
            throw CustomException(
                HttpStatus.BAD_REQUEST,
                ExceptionView.TOAST,
                "받은 메시지만 읽을 수 있습니다."
            )
        }
    }

    fun validateDeleteSendMessage(loginUser: User) {
        require(senderId == loginUser.id) {
            throw CustomException(
                HttpStatus.FORBIDDEN,
                ExceptionView.TOAST,
                "메시지를 삭제할 권한이 없습니다."
            )
        }
    }

    fun validateDeleteReceivedMessage(loginUser: User) {
        require(receiverId == loginUser.id) {
            throw CustomException(
                HttpStatus.FORBIDDEN,
                ExceptionView.TOAST,
                "메시지를 삭제할 권한이 없습니다."
            )
        }
    }

    private fun validateReportMessage(reportSenderId: Long) {
        require(receiverId == reportSenderId) {
            throw CustomException(
                HttpStatus.BAD_REQUEST,
                ExceptionView.TOAST,
                "수신자만이 메시지를 신고할 수 있습니다."
            )
        }
    }

    fun validateReadMessage(loginUser: User) {
        require(senderId == loginUser.id || receiverId == loginUser.id) {
            throw CustomException(
                HttpStatus.FORBIDDEN,
                ExceptionView.TOAST,
                "메시지를 읽을 수 있는 권한이 없습니다."
            )
        }
    }

    fun validateReceivedMessage(loginUser: User) {
        require(messageType == MessageType.RECEIVED) {
            throw CustomException(
                HttpStatus.BAD_REQUEST,
                ExceptionView.TOAST,
                "받은 메시지만 차단이 가능합니다."
            )
        }
        require(senderId != loginUser.id) {
            throw CustomException(
                HttpStatus.BAD_REQUEST,
                ExceptionView.TOAST,
                "받은 메시지만 차단이 가능합니다"
            )
        }
        require(receiverId == loginUser.id) {
            throw CustomException(
                HttpStatus.BAD_REQUEST,
                ExceptionView.TOAST,
                "받은 메시지만 차단이 가능합니다"
            )
        }
    }

    fun reported(senderId: Long): Message {
        validateReportMessage(senderId)
        return this.copy(
            isReceiverDeleted = true,
            isReported = true,
            receiverDeletedAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    fun sendMessageSoftDelete(loginUser: User): Message {
        validateDeleteSendMessage(loginUser)
        validateRegulationUser(loginUser)
        return this.copy(
            isSenderDeleted = true,
            senderDeletedAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    fun receivedMessageSoftDelete(loginUser: User): Message {
        validateDeleteReceivedMessage(loginUser)
        validateRegulationUser(loginUser)
        return this.copy(
            isReceiverDeleted = true,
            receiverDeletedAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    fun receivedMessage(): Message {
        return this.copy(
            messageType = MessageType.RECEIVED,
            receivedAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    companion object {

        private const val adminId = 1L
        private const val adminName = "당신의 큐피트 에버"

        fun sendMessage(
            content: String,
            receiver: User,
            sender: User,
            senderName: String,
            isAnonymous: Boolean
        ): Message {
            validateRegulationUser(sender)
            validateRegulationUser(receiver)
            validateMessageSendTime()
            val message = Message(
                id = 0L,
                content = MessageContent.from(content),
                senderId = sender.id,
                senderName = senderName,
                messageType = MessageType.SENT,
                receiverId = receiver.id,
                isAnonymous = isAnonymous,
                isReceiverRead = false,
                readAt = null,
                isSend = false,
                sendAt = LocalDateTime.now(),
                isReported = false,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                receivedAt = null,
                isSenderDeleted = false,
                senderDeletedAt = null,
                isReceiverDeleted = false,
                receiverDeletedAt = null
            )
            message.validateMessageReceiver()

            return message
        }


        fun createWelcomeMessage(
            receiverId: Long,
            receiverName: String
        ): Message {
            return Message(
                id = 0L,
                content = MessageContent.createWelcomeMessage(receiverName),
                senderId = adminId,
                senderName = adminName,
                receiverId = receiverId,
                isReceiverRead = false,
                isAnonymous = true,
                messageType = MessageType.RECEIVED,
                readAt = null,
                isSend = false,
                sendAt = LocalDateTime.now(),
                receivedAt = LocalDateTime.now(),
                isReported = false,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                isSenderDeleted = false,
                senderDeletedAt = null,
                isReceiverDeleted = false,
                receiverDeletedAt = null
            )
        }

        private fun validateRegulationUser(user: User) {
            if (user.isWithDraw()) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "탈퇴한 학생은 해당 서비스를 이용할 수 없습니다."
                )
            }
            if (user.isKeepRestrict()) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "이용제한을 당한 학생은 해당 서비스를 이용할 수 없습니다."
                )
            }
        }

    }
}
