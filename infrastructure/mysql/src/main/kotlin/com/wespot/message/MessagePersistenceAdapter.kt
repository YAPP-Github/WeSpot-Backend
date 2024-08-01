package com.wespot.message

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import com.wespot.message.port.out.MessagePort
import org.springframework.stereotype.Repository
import java.time.LocalDate

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
        messageType: MessageType,
        receiverId: Long,
        cursorId: Long,
        blockedUserIds: List<Long>,
        pageable: Pageable
    ): List<Message> {
        return messageJpaRepository.findAllByMessageTypeAndReceiverIdAfterCursor(
            messageType = messageType,
            receiverId = receiverId,
            cursorId = cursorId,
            blockedUserIds = blockedUserIds,
            pageable = pageable
        ).map { MessageMapper.mapToDomainEntity(it) }
    }

    override fun findAllMessagesByTypeAndSenderAfterCursor(
        messageType: MessageType,
        senderId: Long,
        cursorId: Long,
        pageable: Pageable
    ): List<Message> {
        return messageJpaRepository.findAllMessagesByTypeAndSenderAfterCursor(
            messageType = messageType,
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

    override fun countMessagesAfterCursor(
        messageType: MessageType,
        receiverId: Long,
        cursorId: Long,
        blockedIds: List<Long>
    ): Long {
        return messageJpaRepository.countMessagesAfterCursor(
            messageType = messageType,
            receiverId = receiverId,
            cursorId = cursorId,
            blockedUserIds = blockedIds
        )
    }

    override fun countSentMessagesAfterCursor(
        messageType: MessageType,
        senderId: Long,
        cursorId: Long
    ): Long {
        return messageJpaRepository.countSentMessagesAfterCursor(
            messageType = messageType,
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
