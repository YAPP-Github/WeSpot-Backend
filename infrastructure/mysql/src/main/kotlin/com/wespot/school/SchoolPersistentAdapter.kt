package com.wespot.school

import com.wespot.school.port.out.SchoolPort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class SchoolPersistentAdapter(
    private val schoolJpaRepository: SchoolJpaRepository
): SchoolPort {

    override fun save(school: School): School {
        return schoolJpaRepository.save(SchoolMapper.mapToJpaEntity(school))
            .let { SchoolMapper.mapToDomainEntity(it) }
    }

    override fun findById(id: Long): School? {
        return schoolJpaRepository.findByIdOrNull(id)
            ?.let { SchoolMapper.mapToDomainEntity(it) }
            ?: throw NoSuchElementException("학교 정보를 찾을 수 없습니다.")
    }

}
