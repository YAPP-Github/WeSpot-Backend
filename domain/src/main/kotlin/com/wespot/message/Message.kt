package com.wespot.message

import com.wespot.message.MessageTimeValidator.validateMessageSendTime
import com.wespot.message.MessageTimeValidator.validateMessageUpdateTime
import com.wespot.user.User
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
        validateMessageOwner(modifier)
        require(senderId != receiverId) { "본인이 메시지를 보낼 수 없습니다." }
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

    fun validateDeleteSendMessage(loginUser: User) {
        require(senderId == loginUser.id) { "메시지를 삭제할 권한이 없습니다." }
    }

    fun validateDeleteReceivedMessage(loginUser: User) {
        require(receiverId == loginUser.id) { "메시지를 삭제할 권한이 없습니다." }
    }

    private fun validateReportMessage(reportSenderId: Long) {
        require(receiverId == reportSenderId) { "수신자만이 메시지를 신고할 수 있습니다." }
    }

    fun validateReadMessage(loginUser: User) {
        require(senderId == loginUser.id || receiverId == loginUser.id) { "메시지를 읽을 수 있는 권한이 없습니다." }
    }

    fun validateReceivedMessage(loginUser: User) {
        require(messageType == MessageType.RECEIVED) { "받은 메시지만 차단이 가능합니다." }
        require(senderId != loginUser.id) { "받은 메시지만 차단이 가능합니다" }
        require(receiverId == loginUser.id) { "받은 메시지만 차단이 가능합니다" }
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
        return this.copy(
            isSenderDeleted = true,
            senderDeletedAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    fun receivedMessageSoftDelete(loginUser: User): Message {
        validateDeleteReceivedMessage(loginUser)
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
                content = MessageContent.from(content),
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
                isSenderDeleted = false,
                senderDeletedAt = null,
                isReceiverDeleted = false,
                receiverDeletedAt = null
            )
            message.validateMessageReceiver()

            return message
        }


        fun createWelcomeMessage(
            senderId: Long,
            receiverId: Long,
            receiverName: String
        ): Message {
            return Message(
                id = 0L,
                content = MessageContent.from("안녕하세요 ${receiverName}님!\n" +
                    "위스팟 행성에 오신 것을 정말 환영해요~\n" +
                    "이곳에선 이름님의 소중한 마음들을 솔직하게 표현할 수 있답니다!\n" +
                    "\n" +
                    "익명 쪽지함은 매일 저녁 5시에 열려 밤 10시에 닫히고 하루에 최대 세 통의 쪽지를 보낼 수 있어요!\n" +
                    "\n" +
                    "제가 큐피트가 되어 ${receiverName}님의 소중한 마음들을 전달해 드릴테니 제가 필요하실 땐 언제든지 쪽지함을 찾아와 주세요!\n" +
                    "\n" +
                    "위스팟에서 설레는 시간을 보내시길 바라며.."),
                senderId = senderId,
                senderName = "당신의 큐피트 에버",
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
    }
}
