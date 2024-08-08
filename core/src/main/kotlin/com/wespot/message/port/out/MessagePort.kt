package com.wespot.message.port.out

import com.wespot.message.Message
import com.wespot.message.MessageType
import org.springframework.data.domain.Pageable

interface MessagePort {

    fun save(message: Message): Message

    fun sendMessageCount(userId: Long): Int

    fun findById(id: Long): Message?

    fun findAllMessagesByTypeAndReceiverAfterCursor(
        receiverId: Long,
        cursorId: Long,
        blockedMessageIds: List<Long>,
        pageable: Pageable
    ): List<Message>

    fun findAllMessagesByTypeAndSenderAfterCursor(
        senderId: Long,
        cursorId: Long,
        pageable: Pageable
    ): List<Message>

    fun hasSentMessageToday(
        senderId: Long,
        receiverId: Long
    ): Boolean

    fun countReceivedMessagesAfterCursor(
        receiverId: Long,
        cursorId: Long,
        blockedMessageIds: List<Long>
    ): Long

    fun countSentMessagesAfterCursor(
        senderId: Long,
        cursorId: Long
    ): Long

    fun findAllScheduledMessages(
        messageType: MessageType,
        senderId: Long,
    ): List<Message>

    fun deleteById(id: Long)

    fun existsByIdAndSenderIdAndReceiverId(id: Long, senderId: Long, receiverId: Long): Boolean
}
