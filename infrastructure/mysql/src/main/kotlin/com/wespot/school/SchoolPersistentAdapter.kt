package com.wespot.school

import com.wespot.school.port.out.SchoolPort
import org.springframework.stereotype.Repository

@Repository
class SchoolPersistentAdapter(
    private val schoolJpaRepository: SchoolJpaRepository
): SchoolPort {

    override fun save(school: School): School {
        return schoolJpaRepository.save(SchoolMapper.mapToJpaEntity(school))
            .let { SchoolMapper.mapToDomainEntity(it) }
    }

}
