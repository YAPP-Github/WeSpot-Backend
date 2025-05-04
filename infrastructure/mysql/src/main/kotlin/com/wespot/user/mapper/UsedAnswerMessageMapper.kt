package com.wespot.user.mapper

import com.wespot.common.BaseEntity
import com.wespot.user.entity.message.UsedAnswerMessageJpaEntity
import com.wespot.user.message.UsedAnswerMessage
import java.time.LocalDateTime

object UsedAnswerMessageMapper {

    fun mapToDomainEntity(usedAnswerMessageJpaEntity: UsedAnswerMessageJpaEntity): UsedAnswerMessage =
        UsedAnswerMessage(
            id = usedAnswerMessageJpaEntity.id,
            userId = usedAnswerMessageJpaEntity.userId,
            isUsedAnswerMessageFeature = usedAnswerMessageJpaEntity.isUsedAnswerMessageFeature,
            createdAt = usedAnswerMessageJpaEntity.baseEntity.createdAt,
        )

    fun mapToJpaEntity(usedAnswerMessage: UsedAnswerMessage): UsedAnswerMessageJpaEntity = UsedAnswerMessageJpaEntity(
        id = usedAnswerMessage.id,
        userId = usedAnswerMessage.userId,
        isUsedAnswerMessageFeature = usedAnswerMessage.isUsedAnswerMessageFeature,
        baseEntity = BaseEntity(usedAnswerMessage.createdAt, LocalDateTime.now())
    )

}
