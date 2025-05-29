package com.wespot.message.fixture

import com.wespot.message.Message
import com.wespot.message.MessageContent
import com.wespot.message.MessageType
import com.wespot.user.fixture.UserFixture
import java.time.LocalDateTime


object MessageFixture {

    fun createWithId(id: Long) = Message(
        id = id,
        content = MessageContent.from("content"),
        senderId = 1,
        senderName = "senderName",
        receiverId = 2,
        isReceiverRead = true,
        isAnonymous = false,
        messageType = MessageType.SENT,
        readAt = LocalDateTime.now(),
        isSend = true,
        sendAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        receivedAt = LocalDateTime.now(),
        isReported = false,
        isSenderDeleted = false,
        senderDeletedAt = null,
        isReceiverDeleted = false,
        receiverDeletedAt = null
    )

    fun createWithIdAndSenderIdAndReceiverId(id: Long, senderId: Long, receiverId: Long) = Message(
        id = id,
        content = MessageContent.from("content"),
        senderId = senderId,
        senderName = "senderName",
        receiverId = receiverId,
        isReceiverRead = true,
        isAnonymous = false,
        readAt = LocalDateTime.now(),
        messageType = MessageType.SENT,
        isSend = true,
        sendAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        receivedAt = LocalDateTime.now(),
        isReported = false,
        isSenderDeleted = false,
        senderDeletedAt = null,
        isReceiverDeleted = false,
        receiverDeletedAt = null
    )

    fun createMessageWithReceived(
        content: String,
        receiverId: Long,
        senderId: Long,
        senderName: String
    ): Message {
        return Message(
            id = 0,
            content = MessageContent.from(content),
            senderId = senderId,
            senderName = senderName,
            receiverId = receiverId,
            isReceiverRead = false,
            isAnonymous = false,
            messageType = MessageType.RECEIVED,
            readAt = null,
            isSend = false,
            sendAt = null,
            receivedAt = null,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            isReported = false,
            isSenderDeleted = false,
            senderDeletedAt = null,
            isReceiverDeleted = false,
            receiverDeletedAt = null
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
            receiver = UserFixture.createWithIdSchool(receiverId),
            sender = UserFixture.createWithIdSchool(senderId),
            senderName = senderName,
            isAnonymous = false,
        )
    }

}
