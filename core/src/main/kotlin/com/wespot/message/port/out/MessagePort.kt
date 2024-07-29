package com.wespot.message.port.out

import com.wespot.message.Message
import com.wespot.message.MessageType
import org.springframework.data.domain.Pageable
import java.time.LocalDate
import java.time.LocalDateTime

interface MessagePort {

    fun save(message: Message): Message

    fun sendMessageCount(userId: Long): Int

    fun findById(id: Long): Message?

    fun findAllMessagesByTypeAndReceiverAfterCursor(
        messageType: MessageType,
        receiverId: Long,
        cursorId: Long,
        blockedUserIds: List<Long>,
        pageable: Pageable
    ): List<Message>

    fun findAllMessagesByTypeAndSenderAfterCursor(
        messageType: MessageType,
        senderId: Long,
        cursorId: Long,
        pageable: Pageable
    ): List<Message>

    fun hasSentMessageToday(
        senderId: Long,
        receiverId: Long
    ): Boolean

    fun countMessagesAfterCursor(
        messageType: MessageType,
        receiverId: Long,
        cursorId: Long,
        blockedIds: List<Long>
    ): Long

    fun countSentMessagesAfterCursor(
        messageType: MessageType,
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
