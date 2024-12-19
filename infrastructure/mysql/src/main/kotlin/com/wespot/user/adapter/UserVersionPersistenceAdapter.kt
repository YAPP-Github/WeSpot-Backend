package com.wespot.user.adapter

import com.wespot.user.UserVersion
import com.wespot.user.mapper.UserVersionMapper
import com.wespot.user.port.out.UserVersionPort
import com.wespot.user.repository.UserVersionJpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserVersionPersistenceAdapter(
    private val userVersionJpaRepository: UserVersionJpaRepository
) : UserVersionPort {

    override fun save(userVersion: UserVersion): UserVersion {
        val userVersionJpaEntity = UserVersionMapper.mapToJpaEntity(userVersion)
        val savedUserVersion = UserVersionMapper.mapToDomainEntity(
            userVersionJpaRepository.save(userVersionJpaEntity)
        )
        return savedUserVersion
    }

    override fun findByUserId(id: Long): UserVersion? {
        return userVersionJpaRepository.findByUserId(id)?.let { UserVersionMapper.mapToDomainEntity(it) }
    }

    override fun findAll(): List<UserVersion> {
        return userVersionJpaRepository.findAll()
            .map { UserVersionMapper.mapToDomainEntity(it) }
    }

}
