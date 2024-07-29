package com.wespot.message.fixture

import com.wespot.message.Message
import com.wespot.message.MessageType
import java.time.LocalDateTime


object MessageFixture {

    fun createWithId(id: Long) = Message(
        id = id,
        content = "content",
        senderId = 1,
        senderName = "senderName",
        receiverId = 2,
        isReceiverRead = true,
        messageType = MessageType.SENT,
        readAt = LocalDateTime.now(),
        isSend = true,
        sendAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        receivedAt = LocalDateTime.now(),
        isDeleted = false,
        deletedAt = null
    )

    fun createWithIdAndSenderIdAndReceiverId(id: Long, senderId: Long, receiverId: Long) = Message(
        id = id,
        content = "content",
        senderId = senderId,
        senderName = "senderName",
        receiverId = receiverId,
        isReceiverRead = true,
        readAt = LocalDateTime.now(),
        messageType = MessageType.SENT,
        isSend = true,
        sendAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        receivedAt = LocalDateTime.now(),
        isDeleted = false,
        deletedAt = null
    )

    fun createMessageWithReceived(
        content: String,
        receiverId: Long,
        senderId: Long,
        senderName: String
    ): Message {
        return Message(
            id = 0,
            content = content,
            senderId = senderId,
            senderName = senderName,
            receiverId = receiverId,
            isReceiverRead = false,
            messageType = MessageType.RECEIVED,
            readAt = null,
            isSend = false,
            sendAt = null,
            receivedAt = null,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            isDeleted = false,
            deletedAt = null
        )
    }

    fun createMessage(
        content: String,
        receiverId: Long,
        senderId: Long,
        senderName: String
    ): Message {
        return Message.sendMessage(
            content = content,
            receiverId = receiverId,
            senderId = senderId,
            senderName = senderName
        )
    }

}
