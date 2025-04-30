package com.wespot.user.adapter

import com.wespot.message.v2.MessageV2JpaRepository
import com.wespot.school.School
import com.wespot.school.SchoolJpaRepository
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
    private val userJpaRepository: UserJpaRepository,
    private val schoolJpaRepository: SchoolJpaRepository
) : AnonymousProfilePort {

    override fun findByProfileId(profileId: Long): AnonymousProfile? {
        return anonymousProfileJpaRepository.findByIdOrNull(profileId)
            ?.let { anonymousProfile ->
                val schoolMap =
                    schoolJpaRepository.findAllByIdIn(listOf(anonymousProfile.ownerId, anonymousProfile.receiverId))
                        .associateBy { it.id }
                AnonymousProfileMapper.mapToDomainEntity(
                    anonymousProfile,
                    ownerJpaEntity = userJpaRepository.findByIdOrNull(anonymousProfile.ownerId)
                        ?: throw IllegalArgumentException("유저를 찾을 수 없습니다."),
                    receiverJpaEntity = userJpaRepository.findByIdOrNull(anonymousProfile.receiverId)
                        ?: throw IllegalArgumentException("유저를 찾을 수 없습니다."),
                    ownerSchoolJpaEntity = schoolMap[anonymousProfile.ownerId]!!,
                    receiverSchoolJpaEntity = schoolMap[anonymousProfile.receiverId]!!
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
        val schoolMap =
            schoolJpaRepository.findAllByIdIn(listOf(ownerId, receiverId))
                .associateBy { it.id }

        return anonymousProfileJpaRepository.findAllByOwnerIdAndReceiverId(ownerId, receiverId)
            .map {
                AnonymousProfileMapper.mapToDomainEntity(
                    anonymousProfileJpaEntity = it,
                    ownerJpaEntity = owner,
                    receiverJpaEntity = receiver,
                    ownerSchoolJpaEntity = schoolMap[ownerId]!!,
                    receiverSchoolJpaEntity = schoolMap[receiverId]!!
                )
            }
    }

}
