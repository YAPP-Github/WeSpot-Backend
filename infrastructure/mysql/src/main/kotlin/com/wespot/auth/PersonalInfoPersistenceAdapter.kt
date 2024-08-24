package com.wespot.auth

import com.wespot.auth.port.out.PersonalInfoPort
import org.springframework.stereotype.Repository

@Repository
class PersonalInfoPersistenceAdapter(
    private val personalInfoJpaRepository: PersonalInfoJpaRepository
): PersonalInfoPort {

    override fun findByEmail(email: String): PersonalInfo? {
        return personalInfoJpaRepository.findByEmail(email)
            ?.let { PersonalInfoMapper.mapToDomainEntity(it) }
    }

    override fun findBySocialId(socialId: String): PersonalInfo? {
        return personalInfoJpaRepository.findBySocialId(socialId)
            ?.let { PersonalInfoMapper.mapToDomainEntity(it) }
    }

    override fun save(personalInfo: PersonalInfo): PersonalInfo {
        return personalInfoJpaRepository.save(PersonalInfoMapper.mapToJpaEntity(personalInfo))
            .let { PersonalInfoMapper.mapToDomainEntity(it) }
    }

}
