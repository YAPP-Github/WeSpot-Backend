package com.wespot.message.v2

import com.wespot.common.BaseEntity
import com.wespot.message.MessageContent
import com.wespot.message.MessageV2

class MessageV2Mapper {

    fun mapToDomainEntity(messageJpaEntityV2: MessageJpaEntityV2): MessageV2 = MessageV2(
        id = messageJpaEntityV2.id,
        content = MessageContent.from(messageJpaEntityV2.content),
        senderId = messageJpaEntityV2.senderId,
        senderName = messageJpaEntityV2.senderName,
        receiverId = messageJpaEntityV2.receiverId,
        isAnonymous = messageJpaEntityV2.isAnonymous,
        messageType = messageJpaEntityV2.messageType,
        isReceiverRead = messageJpaEntityV2.isReceiverRead,
        readAt = messageJpaEntityV2.readAt,
        isSend = messageJpaEntityV2.isSend,
        sendAt = messageJpaEntityV2.sendAt,
        receivedAt = messageJpaEntityV2.receivedAt,
        isReported = messageJpaEntityV2.isReported,
        createdAt = messageJpaEntityV2.baseEntity.createdAt,
        updatedAt = messageJpaEntityV2.baseEntity.updatedAt,
        isSenderDeleted = messageJpaEntityV2.isSenderDeleted,
        senderDeletedAt = messageJpaEntityV2.senderDeletedAt,
        isReceiverDeleted = messageJpaEntityV2.isReceiverDeleted,
        receiverDeletedAt = messageJpaEntityV2.receiverDeletedAt,

        messageRoomId = messageJpaEntityV2.messageRoomId,
        messageRoomOwnerId = messageJpaEntityV2.messageRoomOwnerId,
        anonymousProfileId = messageJpaEntityV2.anonymousProfileId,
        isBookmarked = messageJpaEntityV2.isBookmarked
    )

    fun mapToJpaEntity(messageV2: MessageV2): MessageJpaEntityV2 = MessageJpaEntityV2(
        id = messageV2.id,
        content = messageV2.content.content,
        senderId = messageV2.senderId,
        senderName = messageV2.senderName,
        receiverId = messageV2.receiverId,
        isAnonymous = messageV2.isAnonymous,
        messageType = messageV2.messageType,
        isReceiverRead = messageV2.isReceiverRead,
        readAt = messageV2.readAt,
        isSend = messageV2.isSend,
        sendAt = messageV2.sendAt,
        receivedAt = messageV2.receivedAt,
        isReported = messageV2.isReported,
        baseEntity = BaseEntity(messageV2.createdAt, messageV2.updatedAt),
        isSenderDeleted = messageV2.isSenderDeleted,
        senderDeletedAt = messageV2.senderDeletedAt,
        isReceiverDeleted = messageV2.isReceiverDeleted,
        receiverDeletedAt = messageV2.receiverDeletedAt,

        messageRoomId = messageV2.messageRoomId,
        messageRoomOwnerId = messageV2.messageRoomOwnerId,
        anonymousProfileId = messageV2.anonymousProfileId,
        isBookmarked = messageV2.isBookmarked
    )

}
