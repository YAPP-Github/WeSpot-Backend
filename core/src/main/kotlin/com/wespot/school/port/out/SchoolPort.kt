package com.wespot.school.port.out

import com.wespot.school.School
import org.springframework.data.domain.Pageable

interface SchoolPort {

    fun save(school: School): School

    fun findById(id: Long): School?

    fun searchSchools(
        keyword: String,
        cursorId: Long,
        pageable: Pageable
    ): List<School>

    fun countByNameContainingAndIdGreaterThan(
        keyword: String,
        cursorId: Long
    ): Long

}
