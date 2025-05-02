package com.wespot.message.v2

import com.wespot.common.BaseEntity
import com.wespot.message.MessageContent
import com.wespot.school.SchoolJpaEntity
import com.wespot.user.User
import com.wespot.user.entity.UserJpaEntity
import com.wespot.user.entity.message.AnonymousProfileJpaEntity
import com.wespot.user.mapper.AnonymousProfileMapper
import com.wespot.user.mapper.UserMapper
import com.wespot.user.message.AnonymousProfile

object MessageV2Mapper {

    fun mapToDomainEntity(
        messageJpaEntityV2: MessageJpaEntityV2,
        sender: User,
        receiver: User,
        anonymousProfile: AnonymousProfile?,
    ): MessageV2 = MessageV2(
        id = messageJpaEntityV2.id,
        content = MessageContent.from(messageJpaEntityV2.content),
        sender = sender,
        receiver = receiver,
        isReceiverRead = messageJpaEntityV2.isReceiverRead,
        readAt = messageJpaEntityV2.readAt,

        isBlocked = messageJpaEntityV2.isBlocked,
        isReported = messageJpaEntityV2.isReported,

        createdAt = messageJpaEntityV2.baseEntity.createdAt,
        updatedAt = messageJpaEntityV2.baseEntity.updatedAt,

        isSenderDeleted = messageJpaEntityV2.isSenderDeleted,
        senderDeletedAt = messageJpaEntityV2.senderDeletedAt,
        isReceiverDeleted = messageJpaEntityV2.isReceiverDeleted,
        receiverDeletedAt = messageJpaEntityV2.receiverDeletedAt,

        messageRoomId = messageJpaEntityV2.messageRoomId,
        messageRoomOwnerId = messageJpaEntityV2.messageRoomOwnerId,
        anonymousProfile = anonymousProfile,

        isSenderBookmarked = messageJpaEntityV2.isSenderBookmarked,
        isReceiverBookmarked = messageJpaEntityV2.isReceiverBookmarked,
    )

    fun mapToDomainEntity(
        messageJpaEntityV2: MessageJpaEntityV2,
        senderJpaEntity: UserJpaEntity,
        senderSchoolJpaEntity: SchoolJpaEntity,
        receiverJpaEntity: UserJpaEntity,
        receiverSchoolJpaEntity: SchoolJpaEntity,
        anonymousProfileJpaEntity: AnonymousProfileJpaEntity?,
    ): MessageV2 {
        val sender =
            UserMapper.mapToDomainEntity(userJpaEntity = senderJpaEntity, schoolJpaEntity = senderSchoolJpaEntity)
        val receiver =
            UserMapper.mapToDomainEntity(userJpaEntity = receiverJpaEntity, schoolJpaEntity = receiverSchoolJpaEntity)
        val anonymousProfile =
            if (anonymousProfileJpaEntity == null) null
            else AnonymousProfileMapper.mapToDomainEntity(
                anonymousProfileJpaEntity = anonymousProfileJpaEntity,
                owner = sender,
                receiver = receiver
            )

        return MessageV2(
            id = messageJpaEntityV2.id,
            content = MessageContent.from(messageJpaEntityV2.content),
            sender = sender,
            receiver = receiver,
            isReceiverRead = messageJpaEntityV2.isReceiverRead,
            readAt = messageJpaEntityV2.readAt,

            isBlocked = messageJpaEntityV2.isBlocked,
            isReported = messageJpaEntityV2.isReported,

            createdAt = messageJpaEntityV2.baseEntity.createdAt,
            updatedAt = messageJpaEntityV2.baseEntity.updatedAt,
            isSenderDeleted = messageJpaEntityV2.isSenderDeleted,
            senderDeletedAt = messageJpaEntityV2.senderDeletedAt,
            isReceiverDeleted = messageJpaEntityV2.isReceiverDeleted,
            receiverDeletedAt = messageJpaEntityV2.receiverDeletedAt,

            messageRoomId = messageJpaEntityV2.messageRoomId,
            messageRoomOwnerId = messageJpaEntityV2.messageRoomOwnerId,
            anonymousProfile = anonymousProfile,

            isSenderBookmarked = messageJpaEntityV2.isSenderBookmarked,
            isReceiverBookmarked = messageJpaEntityV2.isReceiverBookmarked,
        )
    }


    fun mapToJpaEntity(messageV2: MessageV2): MessageJpaEntityV2 = MessageJpaEntityV2(
        id = messageV2.id,
        content = messageV2.content.content,
        senderId = messageV2.sender.id,
        receiverId = messageV2.receiver.id,
        isReceiverRead = messageV2.isReceiverRead,
        readAt = messageV2.readAt,

        isBlocked = messageV2.isBlocked,
        isReported = messageV2.isReported,

        baseEntity = BaseEntity(messageV2.createdAt, messageV2.updatedAt),
        isSenderDeleted = messageV2.isSenderDeleted,
        senderDeletedAt = messageV2.senderDeletedAt,
        isReceiverDeleted = messageV2.isReceiverDeleted,
        receiverDeletedAt = messageV2.receiverDeletedAt,

        messageRoomId = messageV2.messageRoomId,
        messageRoomOwnerId = messageV2.messageRoomOwnerId,
        anonymousProfileId = messageV2.anonymousProfile?.id,

        isSenderBookmarked = messageV2.isSenderBookmarked,
        isReceiverBookmarked = messageV2.isReceiverBookmarked
    )

}
