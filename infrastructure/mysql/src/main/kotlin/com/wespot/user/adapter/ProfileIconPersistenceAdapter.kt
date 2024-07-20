package com.wespot.user.adapter

import com.wespot.user.ProfileIcon
import com.wespot.user.mapper.ProfileIconMapper
import com.wespot.user.port.out.ProfileIconPort
import com.wespot.user.repository.ProfileIconJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ProfileIconPersistenceAdapter(
    private val profileIconJpaRepository: ProfileIconJpaRepository
): ProfileIconPort {

    override fun findAll(): List<ProfileIcon> {
        return profileIconJpaRepository.findAll()
            .map { ProfileIconMapper.mapToDomainEntity(it) }
            .toList()
    }
}
