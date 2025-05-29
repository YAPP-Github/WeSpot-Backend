package com.wespot.user.adapter

import com.wespot.user.mapper.UsedAnswerMessageMapper
import com.wespot.user.message.UsedAnswerMessage
import com.wespot.user.port.out.UsedAnswerMessagePort
import com.wespot.user.repository.UsedAnswerMessageJpaRepository
import org.springframework.stereotype.Repository

@Repository
class UsedAnswerMessagePersistenceAdapter(
    private val usedAnswerMessageJpaRepository: UsedAnswerMessageJpaRepository
) : UsedAnswerMessagePort {

    override fun save(usedAnswerMessage: UsedAnswerMessage): UsedAnswerMessage {
        val entity = UsedAnswerMessageMapper.mapToJpaEntity(usedAnswerMessage)
        val savedEntity = usedAnswerMessageJpaRepository.save(entity)

        return UsedAnswerMessageMapper.mapToDomainEntity(savedEntity)
    }

    override fun existsByUserId(userId: Long): Boolean {
        return usedAnswerMessageJpaRepository.existsByUserId(userId = userId)
    }

    override fun findByUserId(userId: Long): UsedAnswerMessage? {
        return usedAnswerMessageJpaRepository.findByUserId(userId = userId)
            ?.let { UsedAnswerMessageMapper.mapToDomainEntity(it) }
    }

}
