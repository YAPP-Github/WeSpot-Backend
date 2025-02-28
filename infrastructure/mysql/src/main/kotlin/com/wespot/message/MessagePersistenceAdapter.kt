package com.wespot.message

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import com.wespot.message.port.out.MessagePort
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime

@Repository
class MessagePersistenceAdapter(
    val messageJpaRepository: MessageJpaRepository
) : MessagePort {

    override fun deleteById(id: Long) {
        messageJpaRepository.deleteById(id)
    }

    override fun existsByIdAndSenderIdAndReceiverId(id: Long, senderId: Long, receiverId: Long): Boolean {
        return messageJpaRepository.existsByIdAndSenderIdAndReceiverId(id, senderId, receiverId)
    }

    override fun findByMessageTypeAndSendAtBefore(sendAt: LocalDateTime): List<Message> {
        return messageJpaRepository.findByMessageTypeAndSendAtBefore(sendAt)
            .map { MessageMapper.mapToDomainEntity(it) }
    }

    override fun countUnreadMessagesByReceiverIdAndBetweenSendTimeAndMessageOpenTime(
        receiverId: Long,
        blockedMessageIds: List<Long>,
        sendTime: LocalDateTime,
        messageOpenTime: LocalDateTime
    ): Long {
        return messageJpaRepository.countUnreadMessagesByReceiverIdAndBetweenSendTimeAndOpenTime(
            receiverId,
            blockedMessageIds,
            sendTime,
            messageOpenTime,
        )
    }

    override fun save(message: Message): Message {
        return messageJpaRepository.save(MessageMapper.mapToJpaEntity(message))
            .let { MessageMapper.mapToDomainEntity(it) }
    }

    override fun sendMessageCount(userId: Long): Int {
        return messageJpaRepository.countMessagesBySenderIdAndDate(
            senderId = userId,
            date = LocalDate.now()
        )
    }

    override fun findById(id: Long): Message? {
        return messageJpaRepository.findByIdOrNull(id)
            ?.let { MessageMapper.mapToDomainEntity(it) }
    }

    override fun findAllMessagesByTypeAndReceiverAfterCursor(
        receiverId: Long,
        cursorId: Long,
        blockedUserIds: List<Long>,
        blockedMessageIds: List<Long>,
        pageable: Pageable
    ): List<Message> {
        return messageJpaRepository.findAllByMessageTypeAndReceiverIdAfterCursor(
            receiverId = receiverId,
            cursorId = cursorId,
            blockedUserIds = blockedUserIds,
            blockedMessageIds = blockedMessageIds,
            pageable = pageable
        ).map { MessageMapper.mapToDomainEntity(it) }
    }

    override fun findAllMessagesByTypeAndSenderAfterCursor(
        senderId: Long,
        cursorId: Long,
        pageable: Pageable
    ): List<Message> {
        return messageJpaRepository.findAllMessagesByTypeAndSenderAfterCursor(
            senderId = senderId,
            cursorId = cursorId,
            pageable = pageable
        ).map { MessageMapper.mapToDomainEntity(it) }
    }

    override fun hasSentMessageToday(
        senderId: Long,
        receiverId: Long
    ): Boolean {
        return messageJpaRepository.existsBySenderIdAndReceiverIdAndDate(
            senderId = senderId,
            receiverId = receiverId,
            date = LocalDate.now()
        )
    }

    override fun countReceivedMessagesAfterCursor(
        receiverId: Long,
        cursorId: Long,
        blockedMessageIds: List<Long>
    ): Long {
        return messageJpaRepository.countReceivedMessagesAfterCursor(
            receiverId = receiverId,
            cursorId = cursorId,
            blockedMessageIds = blockedMessageIds
        )
    }

    override fun countSentMessagesAfterCursor(
        senderId: Long,
        cursorId: Long
    ): Long {
        return messageJpaRepository.countSendMessagesAfterCursor(
            senderId = senderId,
            cursorId = cursorId
        )
    }

    override fun findAllScheduledMessages(
        messageType: MessageType,
        senderId: Long
    ): List<Message> {
        return messageJpaRepository.findAllScheduledMessages(
            messageType = messageType,
            senderId = senderId
        ).map { MessageMapper.mapToDomainEntity(it) }
    }

}
