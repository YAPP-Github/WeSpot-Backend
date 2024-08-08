package com.wespot.school.port.`in`

import com.wespot.school.dto.SchoolListResponse

interface SchoolUseCase {

    fun searchSchools(
        keyword: String,
        cursorId: Long
    ): SchoolListResponse

}

