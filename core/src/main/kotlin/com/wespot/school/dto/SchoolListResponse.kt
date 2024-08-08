package com.wespot.school.dto

data class SchoolListResponse(
    val schools: List<SchoolResponse>,
    val hasNext: Boolean
){
    companion object {

        fun from(schools: List<SchoolResponse>, hasNext: Boolean): SchoolListResponse {
            return SchoolListResponse(
                schools = schools,
                hasNext = hasNext
            )
        }

    }
}
