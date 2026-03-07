package com.wespot.message.v2

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface MessageV2JpaRepository : JpaRepository<MessageJpaEntityV2, Long> {

    fun countBySenderIdAndBaseEntityCreatedAtBetween(senderId: Long, from: LocalDateTime, to: LocalDateTime): Int

    fun findAllByMessageRoomIdIsNullAndSenderIdAndIsSenderBlockedFalse(senderId: Long): List<MessageJpaEntityV2>

    fun findAllByMessageRoomIdIsNullAndReceiverIdAndIsReceiverBlockedFalse(receiverId: Long): List<MessageJpaEntityV2>

    fun findAllByMessageRoomIdIsNullAndSenderIdAndIsSenderBookmarkedTrueAndIsSenderBlockedFalse(senderId: Long): List<MessageJpaEntityV2>

    fun findAllByMessageRoomIdIsNullAndReceiverIdAndIsReceiverBookmarkedTrueAndIsReceiverBlockedFalse(receiverId: Long): List<MessageJpaEntityV2>

    fun findAllByMessageRoomIdIsNullAndSenderIdAndIsSenderBlockedTrue(senderId: Long): List<MessageJpaEntityV2>

    fun findAllByMessageRoomIdIsNullAndReceiverIdAndIsReceiverBlockedTrue(receiverId: Long): List<MessageJpaEntityV2>

    @Query(
        """
            SELECT message
            FROM MessageJpaEntityV2 message
            WHERE message.messageRoomId IN :messageRoomIds
            AND message.baseEntity.createdAt = (
                SELECT MAX(messageInSubquery.baseEntity.createdAt)
                FROM MessageJpaEntityV2 messageInSubquery
                WHERE messageInSubquery.messageRoomId = message.messageRoomId
            )
        """
    )
    fun findAllLastMessageOfRoomByRoomIdIn(messageRoomIds: List<Long>): List<MessageJpaEntityV2>

    fun findAllByMessageRoomId(messageRoomId: Long): List<MessageJpaEntityV2>

    fun findAllByMessageRoomIdIsNullAndSenderIdAndReceiverId(
        senderId: Long,
        receiverId: Long,
    ): List<MessageJpaEntityV2>

    @Query(
        """
            SELECT message
            FROM MessageJpaEntityV2 message
            WHERE message.receiverId = :receiverId
                AND :from <= message.baseEntity.createdAt
                AND message.baseEntity.createdAt = (
                    SELECT MAX(messageInSubquery.baseEntity.createdAt)
                    FROM MessageJpaEntityV2 messageInSubquery
                    WHERE :from <= messageInSubquery.baseEntity.createdAt
                        AND (
                            (message.messageRoomId IS NOT NULL AND message.messageRoomId = messageInSubquery.messageRoomId or message.messageRoomId = messageInSubquery.id)
                            OR
                            (message.messageRoomId IS NULL AND message.id = messageInSubquery.messageRoomId OR message.id = messageInSubquery.id)
                        )
                )
        """
    )
    fun findAllLastMessageOfRoomByReceiverIdAndFromDate(
        receiverId: Long,
        from: LocalDateTime
    ): List<MessageJpaEntityV2>

    fun existsBySenderIdAndReceiverIdAndAnonymousProfileIdIsNull(
        senderId: Long,
        receiverId: Long
    ) :Boolean

    fun deleteBySenderIdOrReceiverId(senderId: Long, receiverId: Long)

}
