package com.wespot.user.adapter

import com.wespot.user.mapper.AnonymousProfileMapper
import com.wespot.user.mapper.UserMapper
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
                        ?.let { user -> UserMapper.mapToDomainEntity(user) }
                )
            }
    }

    override fun save(anonymousProfile: AnonymousProfile): AnonymousProfile {
        val owner = anonymousProfile.owner
        val entity = AnonymousProfileMapper.mapToJpaEntity(anonymousProfile)
        val savedEntity = anonymousProfileJpaRepository.save(entity)

        return AnonymousProfileMapper.mapToDomainEntity(savedEntity, owner)
    }

}
