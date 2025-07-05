package com.wespot.message.v2

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.message.port.out.MessageV2Port
import com.wespot.school.SchoolJpaRepository
import com.wespot.user.entity.UserJpaEntity
import com.wespot.user.entity.message.AnonymousProfileJpaEntity
import com.wespot.user.repository.AnonymousProfileJpaRepository
import com.wespot.user.repository.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Repository
class MessageV2PersistenceAdapter(
    private val messageV2JpaRepository: MessageV2JpaRepository,
    private val userJpaRepository: UserJpaRepository,
    private val anonymousProfileJpaRepository: AnonymousProfileJpaRepository,
    private val schoolJpaRepository: SchoolJpaRepository,
) : MessageV2Port {

    override fun save(messageV2: MessageV2): MessageV2 {
        val sender = messageV2.sender
        val receiver = messageV2.receiver
        val anonymousProfile = messageV2.anonymousProfile
        val messageV2JpaEntity = MessageV2Mapper.mapToJpaEntity(messageV2)
        val savedMessageV2 = messageV2JpaRepository.save(messageV2JpaEntity)

        return MessageV2Mapper.mapToDomainEntity(savedMessageV2, sender, receiver, anonymousProfile)
    }

    override fun countTodaySendMessages(userId: Long): Int {
        val today = LocalDate.now()
        val startOfDay = today.atStartOfDay()
        val endOfDay = LocalDateTime.of(today, LocalTime.MAX)

        return messageV2JpaRepository.countBySenderIdAndBaseEntityCreatedAtBetween(userId, startOfDay, endOfDay)
    }

    override fun findAllMessageRoomBySenderId(senderId: Long): List<MessageV2> {
        val messageRooms = messageV2JpaRepository.findAllByMessageRoomIdIsNullAndSenderId(senderId = senderId)

        return getCompleteMessageV2(messageRooms)
    }

    private fun getCompleteMessageV2(messages: List<MessageJpaEntityV2>): List<MessageV2> {
        val userIds: List<Long> = messages.map { listOf(it.senderId, it.receiverId) }
            .flatMap { it.asSequence() }
            .distinct()

        val users = userJpaRepository.findByIdIn(userIds)
        val usersMap: Map<Long, UserJpaEntity> = users
            .associateBy { it.id }

        val schoolIds = users.map { it.schoolId }
        val schoolsMap = schoolJpaRepository.findAllByIdIn(schoolIds)
            .associateBy { it.id }

        val anonymousProfileIds: List<Long> = messages.filter { it.anonymousProfileId != null }
            .map { it.anonymousProfileId!! }
            .distinct()
        val anonymousProfiles: Map<Long, AnonymousProfileJpaEntity> =
            anonymousProfileJpaRepository.findByIdIn(anonymousProfileIds)
                .associateBy { it.id }

        return messages
            .filter { messageRoom -> usersMap[messageRoom.senderId] != null && usersMap[messageRoom.receiverId] != null }
            .map { messageRoom ->
                val sender = usersMap[messageRoom.senderId]!!
                val receiver = usersMap[messageRoom.receiverId]!!
                val senderSchool = schoolsMap[sender.schoolId]!!
                val receiverSchool = schoolsMap[receiver.schoolId]!!

                MessageV2Mapper.mapToDomainEntity(
                    messageJpaEntityV2 = messageRoom,
                    senderJpaEntity = sender,
                    senderSchoolJpaEntity = senderSchool,
                    receiverJpaEntity = receiver,
                    receiverSchoolJpaEntity = receiverSchool,
                    anonymousProfileJpaEntity = anonymousProfiles[messageRoom.anonymousProfileId]
                )
            }
    }

    override fun findAllMessageRoomByReceiverId(receiverId: Long): List<MessageV2> {
        val messageRooms = messageV2JpaRepository.findAllByMessageRoomIdIsNullAndReceiverId(receiverId = receiverId)
        return getCompleteMessageV2(messageRooms)
    }

    override fun findAllMessageRoomBySenderIdAndIsSenderBookmarkedTrue(senderId: Long): List<MessageV2> {
        val messageRooms =
            messageV2JpaRepository.findAllByMessageRoomIdIsNullAndSenderIdAndIsSenderBookmarkedTrue(senderId = senderId)
        return getCompleteMessageV2(messageRooms)
    }

    override fun findAllMessageRoomByReceiverIdAndIsReceiverBookmarkedTrue(receiverId: Long): List<MessageV2> {
        val messageRooms =
            messageV2JpaRepository.findAllByMessageRoomIdIsNullAndReceiverIdAndIsReceiverBookmarkedTrue(receiverId = receiverId)
        return getCompleteMessageV2(messageRooms)
    }

    override fun findAllMessageRoomBySenderIdAndIsSenderBlockedTrue(senderId: Long): List<MessageV2> {
        val messageRooms =
            messageV2JpaRepository.findAllByMessageRoomIdIsNullAndSenderIdAndIsSenderBlockedTrue(senderId = senderId)
        return getCompleteMessageV2(messageRooms)
    }

    override fun findAllMessageRoomByReceiverIdAndIsReceiverBlockedTrue(receiverId: Long): List<MessageV2> {
        val messageRooms =
            messageV2JpaRepository.findAllByMessageRoomIdIsNullAndReceiverIdAndIsReceiverBlockedTrue(receiverId = receiverId)
        return getCompleteMessageV2(messageRooms)
    }

    override fun findAllLastMessageOfRoomByRoomIdIn(messageRoomIds: List<Long>): List<MessageV2> {
        val lastMessageDetailOfRooms = messageV2JpaRepository.findAllLastMessageOfRoomByRoomIdIn(messageRoomIds)

        return getCompleteMessageV2(lastMessageDetailOfRooms)
    }

    override fun findById(id: Long): MessageV2 {
        val message = messageV2JpaRepository.findByIdOrNull(id) ?: throw CustomException(
            message = "ID에 해당하는 쪽지를 찾을 수 없습니다.",
            view = ExceptionView.TOAST,
            status = HttpStatus.NOT_FOUND
        )

        return getCompleteMessageV2(listOf(message))
            .first()
    }

    override fun findAllByMessageRoomId(messageRoomId: Long): List<MessageV2> {
        val messages = messageV2JpaRepository.findAllByMessageRoomId(messageRoomId = messageRoomId)

        return getCompleteMessageV2(messages)
    }

    override fun findAllMessageRoomBySenderIdAndReceiverId(senderId: Long, receiverId: Long): List<MessageV2> {
        val messageRooms = messageV2JpaRepository.findAllByMessageRoomIdIsNullAndSenderIdAndReceiverId(
            senderId = senderId,
            receiverId = receiverId
        )

        return getCompleteMessageV2(messageRooms)
    }

    override fun findAllLastMessageOfRoomByReceiverIdAndFromDate(receiverId: Long, from: LocalDate): List<MessageV2> {
        val messages =
            messageV2JpaRepository.findAllLastMessageOfRoomByReceiverIdAndFromDate(
                receiverId = receiverId,
                from = from.atStartOfDay()
            )

        return getCompleteMessageV2(messages = messages)
    }

    override fun isExistsBySenderIdAndReceiverIdWithRealName(senderId: Long, receiverId: Long): Boolean {
        return messageV2JpaRepository.existsBySenderIdAndReceiverIdAndAnonymousProfileIdIsNull(
            senderId = senderId,
            receiverId = receiverId
        )
    }

}
