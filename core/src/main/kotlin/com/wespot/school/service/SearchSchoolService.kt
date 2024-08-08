package com.wespot.school.service

import com.wespot.school.dto.SchoolListResponse
import com.wespot.school.dto.SchoolResponse
import com.wespot.school.port.`in`.SchoolUseCase
import com.wespot.school.port.out.SchoolPort
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class SearchSchoolService(
    private val schoolPort: SchoolPort
): SchoolUseCase {

    override fun searchSchools(
        keyword: String,
        cursorId: Long
    ): SchoolListResponse {

        val pageable = PageRequest.of(0, 10, Sort.by("id"))

        val schools = schoolPort.searchSchools(
            keyword = keyword,
            cursorId= cursorId,
            pageable = pageable
        )
        val hasNext = schoolPort.countByNameContainingAndIdGreaterThan(
            keyword = keyword,
            cursorId = cursorId
        ) > 10

        return SchoolListResponse.from(
            schools = schools.map { SchoolResponse.from(it) },
            hasNext = hasNext
        )
    }
}
