package com.wespot.user.adapter

import com.wespot.user.Profile
import com.wespot.user.mapper.ProfileMapper
import com.wespot.user.port.out.ProfilePort
import com.wespot.user.repository.ProfileJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ProfilePersistentAdapter(
    private val profileJpaRepository: ProfileJpaRepository
): ProfilePort {

    override fun save(profile: Profile): Profile {
        return profileJpaRepository.save(ProfileMapper.mapToJpaEntity(profile))
            .let { ProfileMapper.mapToDomainEntity(it) }
    }

}
