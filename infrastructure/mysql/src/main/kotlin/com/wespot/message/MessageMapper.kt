package com.wespot.message

import com.wespot.common.BaseEntity

object MessageMapper {

    fun mapToDomainEntity(messageJpaEntity: MessageJpaEntity): Message =
        Message(
            id = messageJpaEntity.id,
            content = messageJpaEntity.content,
            senderId = messageJpaEntity.senderId,
            senderName = messageJpaEntity.senderName,
            receiverId = messageJpaEntity.receiverId,
            messageType = messageJpaEntity.messageType,
            isReceiverRead = messageJpaEntity.isReceiverRead,
            readAt = messageJpaEntity.readAt,
            isSend = messageJpaEntity.isSend,
            sendAt = messageJpaEntity.sendAt,
            receivedAt = messageJpaEntity.receivedAt,
            createdAt = messageJpaEntity.baseEntity.createdAt,
            updatedAt = messageJpaEntity.baseEntity.updatedAt,
            isDeleted = messageJpaEntity.isDeleted,
            deletedAt = messageJpaEntity.deletedAt
        )

    fun mapToJpaEntity(message: Message): MessageJpaEntity =
        MessageJpaEntity(
            id = message.id,
            content = message.content,
            senderId = message.senderId,
            senderName = message.senderName,
            receiverId = message.receiverId,
            isReceiverRead = message.isReceiverRead,
            messageType = message.messageType,
            readAt = message.readAt,
            isSend = message.isSend,
            sendAt = message.sendAt,
            receivedAt = message.receivedAt,
            baseEntity = BaseEntity(
                createdAt = message.createdAt,
                updatedAt = message.updatedAt
            ),
            isDeleted = message.isDeleted,
            deletedAt = message.deletedAt
        )

}
