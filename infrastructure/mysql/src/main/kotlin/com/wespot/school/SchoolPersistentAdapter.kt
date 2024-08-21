package com.wespot.school

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.school.port.out.SchoolPort
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository

@Repository
class SchoolPersistentAdapter(
    private val schoolJpaRepository: SchoolJpaRepository
) : SchoolPort {

    override fun save(school: School): School {
        return schoolJpaRepository.save(SchoolMapper.mapToJpaEntity(school))
            .let { SchoolMapper.mapToDomainEntity(it) }
    }

    override fun findById(id: Long): School? {
        return schoolJpaRepository.findByIdOrNull(id)
            ?.let { SchoolMapper.mapToDomainEntity(it) }
            ?: throw CustomException(HttpStatus.NOT_FOUND, ExceptionView.TOAST, "학교 정보를 찾을 수 없습니다.")
    }

    override fun searchSchools(keyword: String, cursorId: Long, pageable: Pageable): List<School> {
        return schoolJpaRepository.findAllByNameContainingAndIdGreaterThan(keyword, cursorId, pageable)
            .map { SchoolMapper.mapToDomainEntity(it) }
    }

    override fun countByNameContainingAndIdGreaterThan(keyword: String, cursorId: Long): Long {
        return schoolJpaRepository.countByNameContainingAndIdGreaterThan(keyword, cursorId)
    }

}
