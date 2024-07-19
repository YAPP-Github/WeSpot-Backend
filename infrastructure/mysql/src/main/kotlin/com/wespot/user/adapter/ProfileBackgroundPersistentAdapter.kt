package com.wespot.user.adapter

import com.wespot.user.ProfileBackground
import com.wespot.user.mapper.ProfileBackgroundMapper
import com.wespot.user.port.out.ProfileBackgroundPort
import com.wespot.user.repository.ProfileBackgroundJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ProfileBackgroundPersistentAdapter(
    private val profileBackgroundJpaRepository: ProfileBackgroundJpaRepository
): ProfileBackgroundPort {

    override fun findAll(): List<ProfileBackground> {
       return profileBackgroundJpaRepository.findAll()
            .map { ProfileBackgroundMapper.mapToDomainEntity(it) }
            .toList()
    }

}
