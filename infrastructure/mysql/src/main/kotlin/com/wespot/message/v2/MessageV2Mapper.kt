package com.wespot.message.v2

import com.wespot.common.BaseEntity
import com.wespot.message.MessageContent
import com.wespot.message.MessageV2
import com.wespot.user.User
import com.wespot.user.message.AnonymousProfile

object MessageV2Mapper {

    fun mapToDomainEntity(
        messageJpaEntityV2: MessageJpaEntityV2,
        sender: User,
        receiver: User,
        anonymousProfile: AnonymousProfile?
    ): MessageV2 = MessageV2(
        id = messageJpaEntityV2.id,
        content = MessageContent.from(messageJpaEntityV2.content),
        sender = sender,
        receiver = receiver,
        isReceiverRead = messageJpaEntityV2.isReceiverRead,
        readAt = messageJpaEntityV2.readAt,
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
        isBookmarked = messageJpaEntityV2.isBookmarked
    )

    fun mapToJpaEntity(messageV2: MessageV2): MessageJpaEntityV2 = MessageJpaEntityV2(
        id = messageV2.id,
        content = messageV2.content.content,
        senderId = messageV2.sender.id,
        receiverId = messageV2.receiver.id,
        isAnonymous = messageV2.anonymousProfile != null,
        isReceiverRead = messageV2.isReceiverRead,
        readAt = messageV2.readAt,
        isReported = messageV2.isReported,
        baseEntity = BaseEntity(messageV2.createdAt, messageV2.updatedAt),
        isSenderDeleted = messageV2.isSenderDeleted,
        senderDeletedAt = messageV2.senderDeletedAt,
        isReceiverDeleted = messageV2.isReceiverDeleted,
        receiverDeletedAt = messageV2.receiverDeletedAt,

        messageRoomId = messageV2.messageRoomId,
        messageRoomOwnerId = messageV2.messageRoomOwnerId,
        anonymousProfileId = messageV2.anonymousProfile?.id,
        isBookmarked = messageV2.isBookmarked
    )

}
