package com.wespot.message.fixture

import com.wespot.message.Message
import java.time.LocalDateTime


object MessageFixture {

    fun createWithId(id: Long) = Message(
        id = id,
        content = "content",
        senderId = 1,
        receiverId = 2,
        isReceiverRead = true,
        readAt = LocalDateTime.now(),
        isSent = true,
        sentAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        receivedAt = LocalDateTime.now()
    )

    fun createWithIdAndSenderIdAndReceiverId(id: Long, senderId: Long, receiverId: Long) = Message(
        id = id,
        content = "content",
        senderId = senderId,
        receiverId = receiverId,
        isReceiverRead = true,
        readAt = LocalDateTime.now(),
        isSent = true,
        sentAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        receivedAt = LocalDateTime.now()
    )

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
