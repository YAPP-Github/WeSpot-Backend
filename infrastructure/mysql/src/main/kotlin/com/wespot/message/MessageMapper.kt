package com.wespot.message

import com.wespot.common.BaseEntity

object MessageMapper {

    fun mapToDomainEntity(messageJpaEntity: MessageJpaEntity): Message =
        Message(
            id = messageJpaEntity.id,
            content = MessageContent.from(messageJpaEntity.content),
            senderId = messageJpaEntity.senderId,
            senderName = messageJpaEntity.senderName,
            receiverId = messageJpaEntity.receiverId,
            isAnonymous = messageJpaEntity.isAnonymous,
            messageType = messageJpaEntity.messageType,
            isReceiverRead = messageJpaEntity.isReceiverRead,
            readAt = messageJpaEntity.readAt,
            isSend = messageJpaEntity.isSend,
            sendAt = messageJpaEntity.sendAt,
            receivedAt = messageJpaEntity.receivedAt,
            isReported = messageJpaEntity.isReported,
            createdAt = messageJpaEntity.baseEntity.createdAt,
            updatedAt = messageJpaEntity.baseEntity.updatedAt,
            isSenderDeleted = messageJpaEntity.isSenderDeleted,
            senderDeletedAt = messageJpaEntity.senderDeletedAt,
            isReceiverDeleted = messageJpaEntity.isReceiverDeleted,
            receiverDeletedAt = messageJpaEntity.receiverDeletedAt
        )

    fun mapToJpaEntity(message: Message): MessageJpaEntity =
        MessageJpaEntity(
            id = message.id,
            content = message.content.content,
            senderId = message.senderId,
            senderName = message.senderName,
            receiverId = message.receiverId,
            isAnonymous = message.isAnonymous,
            isReceiverRead = message.isReceiverRead,
            messageType = message.messageType,
            readAt = message.readAt,
            isSend = message.isSend,
            sendAt = message.sendAt,
            receivedAt = message.receivedAt,
            isReported = message.isReported,
            baseEntity = BaseEntity(
                createdAt = message.createdAt,
                updatedAt = message.updatedAt
            ),
            isSenderDeleted = message.isSenderDeleted,
            senderDeletedAt = message.senderDeletedAt,
            isReceiverDeleted = message.isReceiverDeleted,
            receiverDeletedAt = message.receiverDeletedAt
        )

}
