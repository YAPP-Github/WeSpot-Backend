package com.wespot.user.adapter

import com.wespot.user.mapper.AnonymousProfileMapper
import com.wespot.user.message.AnonymousProfile
import com.wespot.user.port.out.AnonymousProfilePort
import com.wespot.user.repository.AnonymousProfileJpaRepository
import com.wespot.user.repository.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class AnonymousProfilePersistenceAdapter(
    private val anonymousProfileJpaRepository: AnonymousProfileJpaRepository,
    private val userJpaRepository: UserJpaRepository
) : AnonymousProfilePort {

    override fun findByProfileId(profileId: Long): AnonymousProfile? {
        return anonymousProfileJpaRepository.findByIdOrNull(profileId)
            ?.let { anonymousProfile ->
                AnonymousProfileMapper.mapToDomainEntity(
                    anonymousProfile,
                    owner = userJpaRepository.findByIdOrNull(anonymousProfile.ownerId)
                        ?: throw IllegalArgumentException("유저를 찾을 수 없습니다."),
                    receiver = userJpaRepository.findByIdOrNull(anonymousProfile.receiverId)
                        ?: throw IllegalArgumentException("유저를 찾을 수 없습니다.")
                )
            }
    }

    override fun save(anonymousProfile: AnonymousProfile): AnonymousProfile {
        val owner = anonymousProfile.owner
        val receiver = anonymousProfile.receiver
        val entity = AnonymousProfileMapper.mapToJpaEntity(anonymousProfile)
        val savedEntity = anonymousProfileJpaRepository.save(entity)

        return AnonymousProfileMapper.mapToDomainEntity(savedEntity, owner, receiver)
    }

    override fun findAllByOwnerIdAndReceiverId(ownerId: Long, receiverId: Long): List<AnonymousProfile> {
        val owner = userJpaRepository.findByIdOrNull(ownerId)
            ?: throw IllegalArgumentException("유저를 찾을 수 없습니다.")
        val receiver = userJpaRepository.findByIdOrNull(receiverId)
            ?: throw IllegalArgumentException("유저를 찾을 수 없습니다.")

        return anonymousProfileJpaRepository.findAllByOwnerIdAndReceiverId(ownerId, receiverId)
            .map { AnonymousProfileMapper.mapToDomainEntity(it, owner, receiver) }
    }

}
