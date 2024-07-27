package com.wespot.message

import com.wespot.common.BaseEntity

object MessageMapper {

    fun mapToDomainEntity(messageJpaEntity: MessageJpaEntity) = Message(
        id = messageJpaEntity.id,
        content = messageJpaEntity.content,
        senderId = messageJpaEntity.senderId,
        receiverId = messageJpaEntity.receiverId,
        isReceiverRead = messageJpaEntity.isReceiverRead,
        readAt = messageJpaEntity.readAt,
        isSent = messageJpaEntity.isSent,
        sentAt = messageJpaEntity.sentAt,
        receivedAt = messageJpaEntity.receivedAt,
        createdAt = messageJpaEntity.baseEntity.createdAt,
        updatedAt = messageJpaEntity.baseEntity.updatedAt
    )

    fun mapToJpaEntity(message: Message) = MessageJpaEntity(
        id = message.id,
        content = message.content,
        senderId = message.senderId,
        receiverId = message.receiverId,
        isReceiverRead = message.isReceiverRead,
        readAt = message.readAt,
        isSent = message.isSent,
        sentAt = message.sentAt,
        receivedAt = message.receivedAt,
        baseEntity = BaseEntity(
            createdAt = message.createdAt,
            updatedAt = message.updatedAt
        )
    )

}
